package com.shopping.inandout.routeservice.activities;

import com.shopping.inandout.service.GetRouteOperation;
import com.shopping.inandout.model.GetRouteInput;
import com.shopping.inandout.model.GetRouteOutput;

import software.amazon.smithy.java.server.RequestContext;

public class GetRouteActivity implements GetRouteOperation {
    GetRouteOutput getRoute(GetRouteInput input, RequestContext context) {
        
    }
}
