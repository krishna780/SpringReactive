package com.spring.reactive.router;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Configuration
public class WebClientEx {
    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .defaultCookie("cookieKey", "cookieVal")
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    private HttpClient httpClient() {
        return HttpClient.create().option(
                ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .responseTimeout(Duration.ofMillis(5000))
                .secure()
                .doOnConnected(connection -> connection.addHandlerLast(
                        new ReadTimeoutHandler(5000,
                                TimeUnit.MICROSECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000,
                                TimeUnit.MILLISECONDS)));

    }
}
