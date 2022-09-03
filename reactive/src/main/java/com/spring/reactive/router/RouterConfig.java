package com.spring.reactive.router;

import com.spring.reactive.handler.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Autowired
    private CustomerHandler handler;

    @Autowired
    SecurityManager securityManager;
    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return route()
                .GET("/route/customers",handler::loadCustomers)
                .GET("/route/customer/{input}",handler::findCustomer)
                .POST("/router/customer/save",handler::saveCustomer)
                .build();
    }

      @Bean
      public  RouterFunction<ServerResponse> routerFunc(){
        return route().path(
                "/route/customers", b1->b1.GET(handler::loadCustomers)
                        .GET("/{input}",handler::findCustomer)
                .before(r-> ServerRequest.from(r).header("X-RequestHeader","Value")
                        .build()))
                .POST("save",handler::loadCustomers)
                .build();
      }
@Bean
      public RouterFunction<ServerResponse> responseRouterFunction(){
        return route().path("/route/customers",
                   b1->b1.nest(accept(APPLICATION_JSON),b2->b2
                           .GET(handler::loadCustomers)
                           .GET("/{input}",handler::findCustomer))
                           .POST("/{save}",handler::saveCustomer))
                .build();
      }

}
