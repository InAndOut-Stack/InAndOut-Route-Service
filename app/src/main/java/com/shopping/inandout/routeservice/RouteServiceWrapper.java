package com.shopping.inandout.routeservice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.shopping.inandout.routeservice.dagger.RouteServiceComponent;
import com.shopping.inandout.routeservice.dagger.DaggerRouteServiceComponent;

import software.amazon.smithy.java.server.Server;

@Singleton
public class RouteServiceWrapper {
    private static final Logger LOGGER = Logger.getLogger(RouteServiceWrapper.class.getName());

    public static void main(String... args) {
        RouteServiceComponent component = DaggerRouteServiceComponent.create();
        RouteServiceWrapper wrapper = component.getRouteServiceWrapper();
    }

    private final Server server;
    private final CountDownLatch latch;

    @Inject
    public RouteServiceWrapper(Server server, CountDownLatch latch) {
        this.server = server;
        this.latch = latch;
    }

    public void run() {
        LOGGER.info("Starting server...");
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Server main thread interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
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
