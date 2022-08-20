package com.spring.reactive.handler;

import com.spring.reactive.dao.CustomerDao;
import com.spring.reactive.dto.Customer;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerHandler {
    private CustomerDao customerDao;

    public Mono<ServerResponse> loadCustomers(ServerRequest serverRequest){
        Flux<Customer> customerList = customerDao.getCustomerList();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(customerList, Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest request){
        int input = Integer.parseInt(request.pathVariable("input"));
        Mono<Customer> customerMono = customerDao.getCustomerList().filter(s -> s.getId() == input).next();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }
    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> map = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
       return ServerResponse.ok().body(map, String.class);
    }

    }