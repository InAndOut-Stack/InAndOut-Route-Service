package com.shopping.inandout.routeservice.dagger.modules;

import dagger.Module;
import dagger.Provides;

import java.net.URI;
import javax.inject.Named;
import javax.inject.Singleton;

import software.amazon.smithy.java.server.Server;
import software.amazon.smithy.java.server.Service;

@Module
public class RouteServerModule {
    @Named("serverUri")
    @Provides
    @Singleton
    public URI provideServerUri() {
        return URI.create(
                System.getenv().getOrDefault("ROUTE_SERVICE_ENDPOINT", "http://0.0.0.0:8888"));
    }

    @Provides
    @Singleton
    public Server provideServer(@Named("serverUri") URI uri, Service service) {
        return Server.builder()
                .endpoints(uri)
                .addService(service)
                .build();
    }
}
