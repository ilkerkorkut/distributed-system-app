package com.ilkerkorkut.request;

import com.ilkerkorkut.model.Node;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import rcp.models.MessageReply;
import rcp.models.MessageRequest;
import rcp.models.MessageServiceGrpc;

@Slf4j
public class VoteRequest {

    public MessageReply request(int port, Node currentNode) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .build();
        MessageServiceGrpc.MessageServiceBlockingStub messageServiceBlockingStub = MessageServiceGrpc.newBlockingStub(managedChannel);
        try {
            MessageRequest messageRequest = MessageRequest.newBuilder()
                    .setIsVoteRequest(true)
                    .setNumberOfVotes(currentNode.getVoteCount().get())
                    .setNodeState(currentNode.getNodeState().getState())
                    .setMessage(currentNode.getId())
                    .setTerm(currentNode.getTerm().get())
                    .build();
            return messageServiceBlockingStub.send(messageRequest);
        } catch (StatusRuntimeException e) {
//            log.error("Node is not working on port : " + port);
        } finally {
            managedChannel.shutdown();
        }
        return null;
    }
}
