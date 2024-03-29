package com.spring.reactive.exception;


import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> map=new HashMap<>();
        Throwable error = getError(request);
        map.put("message", error.getMessage());
        map.put("path",request.path());
        map.put("bad request", error.getCause());
        return map;
    }
}
