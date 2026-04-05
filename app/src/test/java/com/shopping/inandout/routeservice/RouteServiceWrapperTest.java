package com.shopping.inandout.routeservice;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.smithy.java.server.Server;

@ExtendWith(MockitoExtension.class)
class RouteServiceWrapperTest {

    @Mock
    private Server server;

    @Mock
    private CountDownLatch latch;

    private RouteServiceWrapper wrapper;

    @BeforeEach
    void setUp() {
        wrapper = new RouteServiceWrapper(server, latch);
    }

    @Test
    void testRunStartsServerAndWaits() throws InterruptedException {
        wrapper.run();

        verify(server).start();
        verify(latch).await();
    }

    @Test
    void testRunHandlesInterruptedException() throws InterruptedException {
        doThrow(new InterruptedException()).when(latch).await();
        wrapper.run();

        verify(latch).await();
        assert (Thread.currentThread().isInterrupted());
        Thread.interrupted();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testShutdownStopsServer() throws Exception {
        CompletableFuture<Void> shutdownFuture = mock(CompletableFuture.class);
        when(server.shutdown()).thenReturn(shutdownFuture);

        wrapper.shutdown();

        verify(server).shutdown();
        verify(shutdownFuture).get(30, TimeUnit.SECONDS);
        verify(latch).countDown();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testShutdownHandlesInterruptedException() throws Exception {
        CompletableFuture<Void> shutdownFuture = mock(CompletableFuture.class);
        when(server.shutdown()).thenReturn(shutdownFuture);
        when(shutdownFuture.get(anyLong(), any(TimeUnit.class))).thenThrow(new InterruptedException());

        wrapper.shutdown();

        verify(latch).countDown();
        assert (Thread.currentThread().isInterrupted());
        Thread.interrupted();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testShutdownHandlesExecutionException() throws Exception {
        CompletableFuture<Void> shutdownFuture = mock(CompletableFuture.class);
        when(server.shutdown()).thenReturn(shutdownFuture);
        when(shutdownFuture.get(anyLong(), any(TimeUnit.class)))
                .thenThrow(new ExecutionException("Error", new Throwable()));

        wrapper.shutdown();

        verify(latch).countDown();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testShutdownHandlesTimeoutException() throws Exception {
        CompletableFuture<Void> shutdownFuture = mock(CompletableFuture.class);
        when(server.shutdown()).thenReturn(shutdownFuture);
        when(shutdownFuture.get(anyLong(), any(TimeUnit.class))).thenThrow(new TimeoutException());

        wrapper.shutdown();

        verify(latch).countDown();
    }
}
