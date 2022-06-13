package ru.otus.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AppServer {
    private static final Logger logger = LoggerFactory.getLogger(AppServer.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8190)
                .addService(new ServerServiceImpl())
                .build();
        server.start();
        logger.info("server started...");
        server.awaitTermination();
    }
}
