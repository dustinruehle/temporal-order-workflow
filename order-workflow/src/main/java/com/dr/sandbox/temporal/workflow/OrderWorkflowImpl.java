package com.dr.sandbox.temporal.workflow;

import com.dr.sandbox.temporal.TaskQueues;
import com.dr.sandbox.temporal.activity.InventoryActivity;
import com.dr.sandbox.temporal.activity.NotificationActivity;
import com.dr.sandbox.temporal.activity.PaymentActivity;
import com.dr.sandbox.temporal.activity.ShippingActivity;
import com.dr.sandbox.temporal.model.*;
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

    private final ShippingActivity shippingActivities = Workflow.newActivityStub(
            ShippingActivity.class,
            ActivityOptions.newBuilder()
                    .setTaskQueue(TaskQueues.SHIPPING_TASK_QUEUE_NAME)
                    .setStartToCloseTimeout(Duration.ofMinutes(5))
                    .build()
    );

    private final NotificationActivity notificationActivities = Workflow.newActivityStub(
            NotificationActivity.class,
            ActivityOptions.newBuilder()
                    .setTaskQueue(TaskQueues.NOTIFICATION_TASK_QUEUE_NAME)
                    .setStartToCloseTimeout(Duration.ofMinutes(5))
                    .build()
    );

    @Override
    public void processOrder(Order order) {
        logger.info("##### Processing order {}", order);

        InventoryReservationRequest irr = new InventoryReservationRequest(order.orderId(),
                order.customerId(),
                order.items());

        String inventoryReservationRequestId = this.inventoryActivities.reserveItems(irr);

        logger.info("##### Order Id {} \n\t\t\t Inventory Reservation Id {}",
                order.orderId(), inventoryReservationRequestId);

        PaymentRequest pr = new PaymentRequest(order.orderId(),
                order.paymentDetails().cardNumber(),
                order.paymentDetails().expiryDate(),
                order.paymentDetails().cvv(),
                order.paymentDetails().amount()
        );

        String paymentId = this.paymentActivities.processPayment(pr);

        logger.info("##### Order Id {} \n\t\t\t Inventory Reservation Id {} \n\t\t\t Payment Id {}",
                order.orderId(), inventoryReservationRequestId, paymentId);

        ShippingRequest sr = new ShippingRequest(order.orderId(),
                order.customerId(),
                order.shippingAddress(),
                order.items(),
                "EXPRESS",
                null,
                null);

        String shippingId = this.shippingActivities.ship(sr);

        logger.info("##### Order Id {} \n\t\t\t Inventory Reservation Id {} \n\t\t\t Payment Id {} \n\t\t\t Shipping Id {}",
                order.orderId(), inventoryReservationRequestId, paymentId,shippingId);

        NotificationRequest nr = new NotificationRequest(order.orderId(),
                order.customerId(),
                "EMAIL",
                "customer@home.com",
                "Shipping Order",
                "Some message",
                null,
                null);

        String confirmationId = this.notificationActivities.sendConfirmation(nr);

        logger.info("##### Order Id {} \n\t\t\t Inventory Reservation Id {} \n\t\t\t Payment Id {} " +
                        "\n\t\t\t Shipping Id {} \n\t\t\t Confirmation Id {}",
                order.orderId(), inventoryReservationRequestId, paymentId,shippingId, confirmationId);

    }
}
