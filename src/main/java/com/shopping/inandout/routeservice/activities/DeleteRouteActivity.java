package com.shopping.inandout.routeservice.activities;

import com.shopping.inandout.service.DeleteRouteOperation;
import com.shopping.inandout.model.DeleteRouteInput;
import com.shopping.inandout.model.DeleteRouteOutput;

import software.amazon.smithy.java.server.RequestContext;

public class DeleteRouteActivity implements DeleteRouteOperation {
    public DeleteRouteOutput deleteRoute(DeleteRouteInput input, RequestContext context) {
        return DeleteRouteOutput.builder().build();
    } 
}
