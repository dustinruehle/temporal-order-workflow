package com.dr.sandbox.temporal.workflow;

import com.dr.sandbox.temporal.TaskQueues;
import com.dr.sandbox.temporal.activity.InventoryActivity;
import com.dr.sandbox.temporal.activity.PaymentActivity;
import com.dr.sandbox.temporal.model.InventoryReservationRequest;
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

    private final InventoryActivity inventoryActivities = Workflow.newActivityStub(
            InventoryActivity.class,
            ActivityOptions.newBuilder()
                    .setTaskQueue(TaskQueues.INVENTORY_TASK_QUEUE_NAME)
                    .setStartToCloseTimeout(Duration.ofMinutes(5))
                    .build()
    );

    @Override
    public void processOrder(Order order) {
        logger.info("##### Processing order {}", order);

        PaymentRequest pr = new PaymentRequest(order.orderId(),
                order.paymentDetails().cardNumber(),
                order.paymentDetails().expiryDate(),
                order.paymentDetails().cvv(),
                order.paymentDetails().amount()
        );

        String paymentId = this.paymentActivities.process(pr);

        logger.info("##### Order Id {} Payment Id {}", order.orderId(), paymentId);

        InventoryReservationRequest irr = new InventoryReservationRequest(order.orderId(),
                order.customerId(),
                order.items());

        String inventoryReservationRequestId = this.inventoryActivities.reserveItems(irr);

        logger.info("##### Order Id {} \n\t\t\t Payment Id {} \n\t\t\t Inventory Reservation Id {}",
                order.orderId(), paymentId, inventoryReservationRequestId);
    }
}
