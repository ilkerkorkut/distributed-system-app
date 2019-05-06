package com.ilkerkorkut.server;

import com.ilkerkorkut.coordinator.StateCoordinator;
import com.ilkerkorkut.manager.StateManager;
import com.ilkerkorkut.rpc.CommunicationService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ApplicationServer {

    private Server server;

    private final StateManager stateManager;
    private final StateCoordinator stateCoordinator;

    public ApplicationServer(StateManager stateManager) {
        this.stateManager = stateManager;
        this.stateCoordinator = new StateCoordinator(this.stateManager);
    }

    public void start() {
        try {
            this.server = NettyServerBuilder.forPort(stateManager.getCurrentNode().getPort())
                    .addService(new CommunicationService(stateManager, stateCoordinator))
                    .build()
                    .start();
        } catch (IOException e) {
            log.error("Couldn't started server : " + e.getMessage());
        }
        log.info("Server started on port: {} with Node Id: {}", stateManager.getCurrentNode().getPort(), stateManager.getCurrentNode().getId());

        stateCoordinator.schedule();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.warn("*** shutting down gRPC server since JVM is shutting down");
            ApplicationServer.this.stop();
            log.warn("*** server shut down");
        }));
    }

    public void blockUntilShutdown() {
        if (server != null) {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                log.error("Server Interrupted: {}", e.getMessage());
            }
        }
    }

    private void stop() {
        server.shutdown();
    }
}
