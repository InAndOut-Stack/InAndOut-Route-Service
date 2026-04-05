package com.shopping.inandout.routeservice.dagger.modules;

import dagger.Module;
import dagger.Provides;

import java.util.concurrent.CountDownLatch;
import javax.inject.Singleton;

@Module
public class LatchModule {
    @Provides
    @Singleton
    public CountDownLatch provideLatch() {
        return new CountDownLatch(1);
    }
}
