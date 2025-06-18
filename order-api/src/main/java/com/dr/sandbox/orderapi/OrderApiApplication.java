package com.dr.sandbox.orderapi;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApiApplication.class, args);
    }

    @Bean
    public WorkflowClient workflowClient() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        return WorkflowClient.newInstance(service);
    }
}
