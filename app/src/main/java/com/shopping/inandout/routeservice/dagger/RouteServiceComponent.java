package com.shopping.inandout.routeservice.dagger;

import com.shopping.inandout.routeservice.RouteServiceWrapper;
import com.shopping.inandout.routeservice.dagger.modules.RouteServiceModule;
import com.shopping.inandout.routeservice.dagger.modules.RouteServerModule;
import com.shopping.inandout.routeservice.dagger.modules.LatchModule;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        RouteServiceModule.class,
        RouteServerModule.class,
        LatchModule.class
})
public interface RouteServiceComponent {
    RouteServiceWrapper getRouteServiceWrapper();
}
