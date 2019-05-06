package com.ilkerkorkut.manager;

import com.ilkerkorkut.enums.NodeState;
import com.ilkerkorkut.model.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.stream.Collectors;

public class StateManager {

    private final Node node;

    private Set<Node> nodes = new HashSet<>();

    private Set<Integer> otherNodePorts;

    @Getter
    @Setter
    private Timer followerTimer;

    @Getter
    @Setter
    private Timer leaderTimer;

    public StateManager(Node node) {
        this.node = node;
    }

    public Node getCurrentNode() {
        return node;
    }

    public void changeCurrentNodeStateAs(NodeState nodeState) {
        this.node.setNodeState(nodeState);
    }

    public boolean isCurrentNodeIsFollower() {
        return NodeState.FOLLOWER.equals(this.node.getNodeState());
    }

    public boolean isCurrentNodeIsCandidate() {
        return NodeState.CANDIDATE.equals(this.node.getNodeState());
    }

    public boolean isCurrentNodeIsLeader() {
        return NodeState.LEADER.equals(this.node.getNodeState());
    }

    public int getCurrentNodeVoteCount() {
        return this.node.getVoteCount().get();
    }

    public int increaseCurrentNodeVoteCount() {
        return this.node.getVoteCount().incrementAndGet();
    }

    public void resetCurrentNodeVoteCount() {
        this.node.getVoteCount().set(0);
    }

    public int increaseTerm() {
        return this.node.getTerm().incrementAndGet();
    }

    public int getCurrentTerm() {
        return this.node.getTerm().get();
    }

    public boolean addExternalNode(Node node) {
        return nodes.add(node);
    }

    public Set<Integer> getOtherNodePorts() {
        return otherNodePorts;
    }

    public void setOtherNodePorts(Set<Integer> ports) {
        this.otherNodePorts = ports.stream().filter(port -> this.node.getPort() != port).collect(Collectors.toSet());
    }
}
