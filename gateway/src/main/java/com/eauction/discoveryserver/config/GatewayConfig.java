package com.eauction.discoveryserver.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import reactor.netty.http.client.HttpClient;

@Configuration
public class GatewayConfig {

    @Value("${seller.service.name}")
    private String sellerServiceName;

    @Value("${buyer.service.name}")
    private  String buyerServiceName;

    @Value("${user.service.name}")
    private String userServiceName;

    @Value("${read.service.name}")
    private String readServiceName;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("seller-route", r -> r.path("/e-auction/api/v1/seller/**")
                    .uri("lb://" + sellerServiceName))
                .route("buyer-route", r -> r.path("/e-auction/api/v1/buyer/**")
                    .uri("lb://" + buyerServiceName))
                .route("user-route", r -> r.path("/e-auction/api/v1/user/**")
                    .uri("lb://" + userServiceName))
                .route("read-route", r -> r.path("/e-auction/api/v1/read/**")
                        .uri("lb://" + readServiceName))
                .build();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
