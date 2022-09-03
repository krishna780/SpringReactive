package com.spring.reactive.controller;

import com.spring.reactive.dto.Customer;
import com.spring.reactive.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerController {

    private CustomerService service;

    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return service.loadAllCustomers();
    }

    @GetMapping(value = "/customersStream" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getCustomersStream(){

        return service.loadAllCustomersStream();
    }
}
