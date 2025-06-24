package com.dr.sandbox.orderapi;

import com.dr.sandbox.temporal.TaskQueues;
import com.dr.sandbox.temporal.model.Address;
import com.dr.sandbox.temporal.model.Order;
import com.dr.sandbox.temporal.workflow.OrderWorkflow;
import com.dr.sandbox.temporal.workflow.OrderWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    public static final Logger logger = Workflow.getLogger(OrderController.class);

    private final WorkflowClient workflowClient;

    @Autowired
    public OrderController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();
        String workflowId = "ORDER_" + orderId;

        Address orderAddress = new Address(orderRequest.shippingAddress().street(),
                orderRequest.shippingAddress().city(),
                orderRequest.shippingAddress().state(),
                orderRequest.shippingAddress().postalCode(),
                orderRequest.shippingAddress().country());

        Order order = new Order(orderRequest.paymentDetails(),
                orderRequest.items(),
                orderAddress,
                orderRequest.customerId(),
                orderId);

        logger.info("######################################");
        logger.info("##### Order Request {}", orderRequest);

        logger.info("##### Creating order {} for workflow {}", orderId, workflowId);

        OrderWorkflow workflow = workflowClient.newWorkflowStub(
                OrderWorkflow.class,
                WorkflowOptions.newBuilder()
                    .setTaskQueue(TaskQueues.ORDER_TASK_QUEUE_NAME)
                    .setWorkflowId(workflowId)
                    .build());
        WorkflowClient.start(workflow::processOrder, order);

        logger.info("##### Workflow started...{}", workflowId);
        logger.info("######################################");

        return workflowId;
    }
}
