package com.ilkerkorkut;

import com.ilkerkorkut.enums.NodeState;
import com.ilkerkorkut.manager.StateManager;
import com.ilkerkorkut.model.Node;
import com.ilkerkorkut.server.ApplicationServer;
import com.ilkerkorkut.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class App {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Needed port arguments. eg: 8090 8091 8092");
            return;
        }

        int currentPort = 8090;

        Set<Integer> ports = new HashSet<>();

        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                currentPort = Integer.valueOf(args[0]);
            } else {
                ports.add(Integer.valueOf(args[i]));
            }
        }

        startNode(currentPort, ports);
    }

    private static void startNode(int port, Set<Integer> otherNodePorts) {
        final long startTime = System.nanoTime();
        final String nodeId = AppUtils.getRandomUUID();

        log.info("Node starting with id {} , port {} at nanoTime {}", nodeId, port, startTime);

        /**
         * Creating node
         */
        Node node = Node.builder()
                .id(nodeId)
                .nodeState(NodeState.FOLLOWER)
                .port(port)
                .voteCount(new AtomicInteger(0))
                .term(new AtomicInteger(0))
                .build();
        /**
         * Creating state manager
         */
        StateManager stateManager = new StateManager(node);
        stateManager.setOtherNodePorts(otherNodePorts);

        /**
         * Creating application server
         */
        final ApplicationServer applicationServer = new ApplicationServer(stateManager);
        applicationServer.start();
        applicationServer.blockUntilShutdown();
    }
}
