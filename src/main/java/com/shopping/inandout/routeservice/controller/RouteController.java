package com.shopping.inandout.routeservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores/{storeId}/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public RouteSummary createRoute(
            @PathVariable String storeId,
            @RequestBody CreateRouteInput request) {

        return routeService.createRoute(storeId, request);
    }

    @GetMapping("/{routeId}")
    public RouteSummary getRoute(
            @PathVariable String storeId,
            @PathVariable String routeId) {

        return routeService.getRoute(storeId, routeId);
    }

    @DeleteMapping("/{routeId}")
    public RouteSummary deleteRoute(
            @PathVariable String storeId,
            @PathVariable String routeId) {

        return routeService.deleteRoute(storeId, routeId);
    }
}