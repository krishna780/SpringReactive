package com.spring.reactive.service;

import com.spring.reactive.dao.CustomerDao;
import com.spring.reactive.dto.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerDao dao;

    public List<Customer> loadAllCustomers(){
        long millis = System.currentTimeMillis();
        List<Customer> customer = dao.getCustomer();
        long end = System.currentTimeMillis();
        System.out.println("total execution time" +(end-millis));
        return customer;

    }

    public Flux<Customer> loadAllCustomersStream(){
        long millis = System.currentTimeMillis();
        Flux<Customer> customerFlux = dao.getCustomersStream();
        long end = System.currentTimeMillis();
        System.out.println("total execution time" +(end-millis));
        return customerFlux;

    }
}
