package com.shopping.inandout.routeservice;

import com.shopping.inandout.service.RouteService;
import com.shopping.inandout.routeservice.activities.CreateRouteActivity;
import com.shopping.inandout.routeservice.activities.DeleteRouteActivity;
import com.shopping.inandout.routeservice.activities.GetRouteActivity;

import java.net.URI;
import java.util.logging.Logger;
import java.util.concurrent.ExecutionException;

import software.amazon.smithy.java.server.Service;
import software.amazon.smithy.java.server.Server;

public class RouteServer implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(RouteServer.class.getName());

    public static void main(String... args) throws RuntimeException {
        new RouteServer().run();
    }

    @Override
    public void run() {
        Service service = RouteService.builder()
                .addCreateRouteOperation(new CreateRouteActivity())
                .addDeleteRouteOperation(new DeleteRouteActivity())
                .addGetRouteOperation(new GetRouteActivity())
                .build();

        String endpoint = System.getenv()
                .getOrDefault("ROUTE_SERVICE_ENDPOINT", "http://0.0.0.0:8888");
        Server server = Server.builder()
                .endpoints(URI.create(endpoint))
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
