package com.selflearning.tw.webflux.practice.router;

import com.selflearning.tw.webflux.practice.handler.SampleHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class SampleRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(SampleHandlerFunction handlerFunction){

        return RouterFunctions
                .route(GET("/functional/flux").and(accept(MediaType.APPLICATION_JSON)), handlerFunction::flux)
                .andRoute(GET("/functional/mono").and(accept(MediaType.APPLICATION_JSON)),handlerFunction::mono);

    }

//    @Bean
//    public RouterFunction<ServerResponse> route(SampleHandler sampleHandler) {
//        System.out.println("in router");
//        return RouterFunctions.route(GET("/functional/flux")
//                        .and(accept(MediaType.APPLICATION_JSON))
//                                    , sampleHandler::getFlux);
//    }
}
