package com.spring.reactive.dao;

import com.spring.reactive.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    public List<Customer> getCustomer() {
        return IntStream.range(1,50)
                .peek(CustomerDao::threadSleep)
                .peek(s-> System.out.println("processing count:"+s))
                .mapToObj(s->new Customer(s, "customer"+s))
                .collect(Collectors.toList());
    }
    private static void threadSleep(int s)  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Flux<Customer> getCustomersStream() {
        return Flux.range(1,50)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i-> System.out.println("processing count"+i))
                .map(i->new Customer(i,"customer"+i));
    }

    public Flux<Customer> getCustomerList() {
        return Flux.range(1,50)
                .doOnNext(i-> System.out.println("processing count"+i))
                .map(i->new Customer(i,"customer"+i));
    }
}
