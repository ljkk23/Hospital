package edu.swu.cs.config;

import edu.swu.cs.filter.TokenFilter;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.function.Function;

@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
    //设置filter
//    @Bean
//    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/service-user/**")
//                        .filters(new Function<GatewayFilterSpec, UriSpec>() {
//                            @Override
//                            public UriSpec apply(GatewayFilterSpec gatewayFilterSpec) {
//                                return gatewayFilterSpec
//                                        .rewritePath("/api/(?<segment>.*)","/$\\{segment}")
//                                        .filter(new TokenFilter());
//                            }
//                        })
//                        .uri("lb://service-user")
//                        .order(0)
//                        .id("service-user_route")
//
//                )
//                .build();
//    }
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}