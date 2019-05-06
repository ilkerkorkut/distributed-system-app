package com.ilkerkorkut.coordinator;

import com.ilkerkorkut.enums.NodeState;
import com.ilkerkorkut.manager.StateManager;
import com.ilkerkorkut.request.HeartbeatRequest;
import com.ilkerkorkut.request.VoteRequest;
import com.ilkerkorkut.utils.AppUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rcp.models.MessageReply;

import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@RequiredArgsConstructor
public class StateCoordinator {

    private final StateManager stateManager;

    private volatile Timer followerTimer = new Timer();
    private Timer leaderTimer = new Timer();

    /**
     * Scheduling needed jobs.
     */
    public void schedule() {
        followerTimer = followerTimer(AppUtils.getRandomElectionTimeout());
        leaderHeartbeat();
    }

    /**
     * Resets election timeout for current node.
     */
    public synchronized void resetElectionTimeout() {
        stateManager.changeCurrentNodeStateAs(NodeState.FOLLOWER);
        stateManager.resetCurrentNodeVoteCount();
        if (Objects.nonNull(followerTimer)) {
            followerTimer.cancel();
        }
        followerTimer = followerTimer(AppUtils.getRandomElectionTimeout());
    }

    /**
     * FollowerTimer for setting candidate state.
     *
     * @param delay
     * @return Timer
     */
    private Timer followerTimer(int delay) {
        followerTimer.cancel();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (stateManager.isCurrentNodeIsFollower()) {
                    candidateVotingRequest();
                }
            }
        };
        timer.schedule(timerTask, delay, Integer.MAX_VALUE);
        return timer;
    }

    private void candidateVotingRequest() {
        stateManager.changeCurrentNodeStateAs(NodeState.CANDIDATE);
        VoteRequest voteRequest = new VoteRequest();
        if (stateManager.isCurrentNodeIsCandidate()) {

            stateManager.increaseTerm();

            Set<Integer> votedNodes = ConcurrentHashMap.newKeySet();
            Set<Integer> downNodes = ConcurrentHashMap.newKeySet();

            stateManager.getOtherNodePorts().parallelStream()
                    .forEach(port -> requestAVote(voteRequest, votedNodes, downNodes, port));

            /**
             * Some nodes may be down or not ready,
             * So In this case do we have to wait them or just electing leader with excluding down nodes.
             * In this case I choose to exclude down node, If some of them will up after, It will remain as follower state.
             */
            int totalNeededVotes = stateManager.getOtherNodePorts().size() - downNodes.size();
            if (stateManager.getCurrentNodeVoteCount() == totalNeededVotes) {
                if (votedNodes.size() == stateManager.getCurrentNodeVoteCount()) {
                    stateManager.changeCurrentNodeStateAs(NodeState.LEADER);
                    log.info("We are started !");
                    log.info("This Node is LEADER : {}", stateManager.getCurrentNode().getPort());
                    log.info(" - voted by nodes: {}", votedNodes.toString());
                    log.info("Current vote count: {}", stateManager.getCurrentNodeVoteCount());
                } else {
                    resetElectionTimeout();
                }
            } else {
                resetElectionTimeout();
            }
        }
    }

    private void requestAVote(VoteRequest voteRequest, Set<Integer> votedNodes, Set<Integer> downNodes, Integer port) {
        MessageReply messageReply = voteRequest.request(port, stateManager.getCurrentNode());
        if (Objects.isNull(messageReply)) {
            downNodes.add(port);
        } else {
            /**
             * Just for clarification , same node will not vote again and not increase vote count for it.
             */
            if (messageReply.getIsVoted() && NodeState.FOLLOWER.getState() == messageReply.getNodeState() && stateManager.getCurrentTerm() > messageReply.getTerm()) {
                if (votedNodes.add(messageReply.getPort())) {
                    stateManager.increaseCurrentNodeVoteCount();
                }
            }
        }
    }

    /**
     * Leader Node's heartbeat scheduled job.
     */
    private void leaderHeartbeat() {
        HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (stateManager.isCurrentNodeIsLeader()) {
                    stateManager.getOtherNodePorts().parallelStream()
                            .forEach(port -> heartbeatRequest.request(port, stateManager.getCurrentNode()));
                }
            }
        };
        leaderTimer.schedule(timerTask, 0, AppUtils.getHeartBeatTimeout());
    }
}
