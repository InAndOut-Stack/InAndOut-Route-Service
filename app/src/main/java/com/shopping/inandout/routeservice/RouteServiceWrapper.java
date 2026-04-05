package com.shopping.inandout.routeservice;

import com.shopping.inandout.service.RouteService;

import com.shopping.inandout.routeservice.activities.CreateRouteActivity;
import com.shopping.inandout.routeservice.activities.DeleteRouteActivity;
import com.shopping.inandout.routeservice.activities.GetRouteActivity;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import software.amazon.smithy.java.server.Service;
import software.amazon.smithy.java.server.Server;

public class RouteServiceWrapper {
    private static final Logger LOGGER = Logger.getLogger(RouteServiceWrapper.class.getName());

    public static void main(String... args) throws RuntimeException {
        URI uri = URI.create(
                System.getenv()
                        .getOrDefault("ROUTE_SERVICE_ENDPOINT", "http://0.0.0.0:8888"));

        Service service = RouteService.builder()
                .addCreateRouteOperation(new CreateRouteActivity())
                .addDeleteRouteOperation(new DeleteRouteActivity())
                .addGetRouteOperation(new GetRouteActivity())
                .build();

        Server server = Server.builder()
                .endpoints(uri)
                .addService(service)
                .build();

        CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> shutdown(server, latch)));
        
        LOGGER.info("Starting server...");
        server.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void shutdown(Server server, CountDownLatch latch) {
        LOGGER.info("Stopping server...");
        try {
            server.shutdown().get(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error shutting down was interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException | TimeoutException e) {
            LOGGER.log(Level.SEVERE, "Error shutting down server", e);
        } finally {
            latch.countDown();
        }
    }
}
