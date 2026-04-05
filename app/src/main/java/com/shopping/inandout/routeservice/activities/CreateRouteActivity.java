package com.shopping.inandout.routeservice.activities;

import com.shopping.inandout.service.CreateRouteOperation;
import com.shopping.inandout.model.CreateRouteInput;
import com.shopping.inandout.model.CreateRouteOutput;

import software.amazon.smithy.java.server.RequestContext;

public class CreateRouteActivity implements CreateRouteOperation {
    public CreateRouteOutput createRoute(CreateRouteInput input, RequestContext context) {
        throw new UnsupportedOperationException("Not (yet) supported operation");
    }
}
