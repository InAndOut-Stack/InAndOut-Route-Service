package com.shopping.inandout.routeservice.activities;

import com.shopping.inandout.service.CreateRouteOperation;
import com.shopping.inandout.model.CreateRouteInput;
import com.shopping.inandout.model.CreateRouteOutput;

import software.amazon.smithy.java.server.RequestContext;

// i love my girlfriend <33333333333

public class CreateRouteActivity implements CreateRouteOperation {
    CreateRouteOutput createRoute(CreateRouteInput input, RequestContext context);
    
}
