package com.dr.sandbox.orderapi;

import com.dr.sandbox.temporal.TaskQueues;
import com.dr.sandbox.temporal.model.Address;
import com.dr.sandbox.temporal.model.Order;
import com.dr.sandbox.temporal.workflow.OrderWorkflow;
import com.dr.sandbox.temporal.workflow.OrderWorkflowImpl;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowExecutionDescription;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelOrder(@RequestBody CancelRequest cancelRequest) {
        logger.info("\n\n\t\t\t##### Canceling order {}\n\n\t\t\t", cancelRequest.workflowId());
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, cancelRequest.workflowId());
        workflow.cancelOrder(cancelRequest.workflowId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{workflowId}")
    public ResponseEntity<Map<String, String>> getOrderStatus(@PathVariable("workflowId") String workflowId) {
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, workflowId);
        String status = workflow.getCurrentStatus();

        Map<String, String> result = new HashMap<>();
        result.put("workflowId", workflowId);
        result.put("status", status);
        return ResponseEntity.ok(result);
    }

}
