package com.dr.sandbox.temporal.workflow;

import com.dr.sandbox.temporal.TaskQueues;
import com.dr.sandbox.temporal.activity.PaymentActivity;
import com.dr.sandbox.temporal.model.Order;
import com.dr.sandbox.temporal.model.PaymentRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class OrderWorkflowImpl implements OrderWorkflow {
    public static final Logger logger = Workflow.getLogger(OrderWorkflowImpl.class);

    private final PaymentActivity paymentActivities = Workflow.newActivityStub(
            PaymentActivity.class,
            ActivityOptions.newBuilder()
                    .setTaskQueue(TaskQueues.PAYMENT_TASK_QUEUE_NAME)
                    .setStartToCloseTimeout(Duration.ofMinutes(5))
                    .build()
    );

    @Override
    public void processOrder(Order order) {
        logger.info("Processing order {}", order);

        PaymentRequest pr = new PaymentRequest(order.orderId(),
                order.paymentDetails().cardNumber(),
                order.paymentDetails().expiryDate(),
                order.paymentDetails().cvv(),
                order.paymentDetails().amount()
        );

        this.paymentActivities.process(pr);

    }
}
