package com.shopping.inandout.routeservice.dagger.modules;

import com.shopping.inandout.service.RouteService;
import com.shopping.inandout.routeservice.activities.CreateRouteActivity;
import com.shopping.inandout.routeservice.activities.DeleteRouteActivity;
import com.shopping.inandout.routeservice.activities.GetRouteActivity;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import software.amazon.smithy.java.server.Service;

@Module
public class RouteServiceModule {
    @Provides
    @Singleton
    public CreateRouteActivity provideCreateRouteActivity() {
        return new CreateRouteActivity();
    }

    @Provides
    @Singleton
    public DeleteRouteActivity provideDeleteRouteActivity() {
        return new DeleteRouteActivity();
    }

    @Provides
    @Singleton
    public GetRouteActivity provideGetRouteActivity() {
        return new GetRouteActivity();
    }

    @Provides
    @Singleton
    public Service provideService(
            CreateRouteActivity createRouteActivity,
            DeleteRouteActivity deleteRouteActivity,
            GetRouteActivity getRouteActivity) {
        return RouteService.builder()
                .addCreateRouteOperation(createRouteActivity)
                .addDeleteRouteOperation(deleteRouteActivity)
                .addGetRouteOperation(getRouteActivity)
                .build();
    }
}
