package com.spring.reactive.scheduler;

import com.spring.reactive.controller.CustomerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Configuration
@EnableScheduling
public class DynamicScheduling implements SchedulingConfigurer {

    @Autowired
    CustomerController controller;
    public Executor executor(){
        return Executors.newCachedThreadPool();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(executor());
        taskRegistrar.addTriggerTask(() -> controller.getCustomers(),
                triggerContext -> {
                    Optional<Date> date = Optional.ofNullable(triggerContext.lastCompletionTime());
                    Instant instant = date.orElseGet(Date::new).toInstant().plusMillis(100);
                    return Date.from(instant);
                }
        );
    }
}
