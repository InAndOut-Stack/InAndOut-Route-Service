package com.shopping.inandout.routeservice;

import com.shopping.inandout.service.InAndOut;
import com.shopping.inandout.routeservice.activities.CreateRouteActivity;
import com.shopping.inandout.routeservice.activities.DeleteRouteActivity;
import com.shopping.inandout.routeservice.activities.GetRouteActivity;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.concurrent.ExecutionException;

import software.amazon.smithy.java.server.Server;

public class RouteService implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(RouteService.class.getName());
    // private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String... args) throws InterruptedException, ExecutionException {
        new RouteService().run();
    }

    @Override
    public void run() {
        Service service = InAndOut.builder()
                .addCreateRouteOperation(new CreateRouteActivity())
                .addGetRouteOperation(new GetRouteActivity())
                .addDeleteRouteOperation(new DeleteRouteActivity())
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
