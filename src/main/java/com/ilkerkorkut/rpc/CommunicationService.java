package com.ilkerkorkut.rpc;

import com.ilkerkorkut.coordinator.StateCoordinator;
import com.ilkerkorkut.manager.StateManager;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import rcp.models.MessageReply;
import rcp.models.MessageRequest;
import rcp.models.MessageServiceGrpc;

@Slf4j
public class CommunicationService extends MessageServiceGrpc.MessageServiceImplBase {

    private final StateManager stateManager;
    private final StateCoordinator stateCoordinator;

    public CommunicationService(StateManager stateManager, StateCoordinator stateCoordinator) {
        this.stateManager = stateManager;
        this.stateCoordinator = stateCoordinator;
    }

    @Override
    public void send(MessageRequest messageRequest, StreamObserver<MessageReply> responseObserver) {
        voteResponse(messageRequest, responseObserver);
        heartbeatResponse(messageRequest, responseObserver);
    }

    private void voteResponse(MessageRequest messageRequest, StreamObserver<MessageReply> responseObserver) {
        if (messageRequest.getIsVoteRequest()) {
            if (stateManager.isCurrentNodeIsFollower() && stateManager.getCurrentNodeVoteCount() == 0) {
                stateCoordinator.resetElectionTimeout();
                MessageReply messageReply = MessageReply.newBuilder()
                        .setNodeId(stateManager.getCurrentNode().getId())
                        .setNodeState(stateManager.getCurrentNode().getNodeState().getState())
                        .setNumberOfVotes(stateManager.getCurrentNodeVoteCount())
                        .setIsVoted(true)
                        .setIsHeartbeat(false)
                        .setTerm(stateManager.getCurrentNode().getTerm().get())
                        .setPort(stateManager.getCurrentNode().getPort())
                        .build();

                responseObserver.onNext(messageReply);
                responseObserver.onCompleted();

                stateManager.getCurrentNode().getTerm().set(messageRequest.getTerm());
            } else {
                MessageReply messageReply = MessageReply.newBuilder()
                        .setNodeId(stateManager.getCurrentNode().getId())
                        .setNodeState(stateManager.getCurrentNode().getNodeState().getState())
                        .setNumberOfVotes(stateManager.getCurrentNodeVoteCount())
                        .setIsVoted(false)
                        .setIsHeartbeat(false)
                        .setTerm(stateManager.getCurrentNode().getTerm().get())
                        .setPort(stateManager.getCurrentNode().getPort())
                        .build();

                responseObserver.onNext(messageReply);
                responseObserver.onCompleted();
            }
        }
    }

    private void heartbeatResponse(MessageRequest messageRequest, StreamObserver<MessageReply> responseObserver) {
        if (messageRequest.getIsHeartbeat()) {
//            log.info("Got heartbeat currentPort {} from Leader port: {}", stateManager.getCurrentNode().getPort(), messageRequest.getPort());
            stateCoordinator.resetElectionTimeout();
            MessageReply messageReply = MessageReply.newBuilder()
                    .setNodeId(stateManager.getCurrentNode().getId())
                    .setNodeState(stateManager.getCurrentNode().getNodeState().getState())
                    .setIsVoted(false)
                    .setIsHeartbeat(true)
                    .setTerm(stateManager.getCurrentNode().getTerm().get())
                    .setPort(stateManager.getCurrentNode().getPort())
                    .build();
            responseObserver.onNext(messageReply);
            responseObserver.onCompleted();

            stateManager.getCurrentNode().getTerm().set(messageRequest.getTerm());
        }
    }
}
