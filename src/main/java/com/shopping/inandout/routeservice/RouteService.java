package com.shopping.inandout.routeservice.service;

import com.shopping.inandout.model.CreateRoute;
import com.shopping.inandout.model.GetRoute;
import com.shopping.inandout.model.DeleteRoute;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import software.amazon.smithy.java.server.Server;
import software.amazon.smithy.java.server.Server;

public class RouteService implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(RouteService.class.getName());
    // private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String... args) throws InterruptedException, ExecutionException {
        new RouteService().run();
    }

    @Override
    public void run() {
        var service = InAndOut.builder()
                .addCreateRouteOperation(new CreateRoute())
                .addGetRouteOperation(new GetRoute())
                .addDeleteRouteOperation(new DeleteRoute())
                .build();

        Server server = Server.builder()
                .endpoints(URI.create("https://localhost:8888"))
                .addService(service)
                .build();
        
        LOGGER.info("Starting server...");
        server.start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            LOGGER.info("Stopping server...");
            try {
                server.shutdown().get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
