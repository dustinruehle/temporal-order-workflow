package com.dr.sandbox.temporal.activityimpl;

import com.dr.sandbox.temporal.activity.NotificationActivity;
import com.dr.sandbox.temporal.model.NotificationRequest;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class NotificationActivityImpl implements NotificationActivity {
    public static final Logger logger = Workflow.getLogger(NotificationActivityImpl.class);

    @Override
    public String sendConfirmation(NotificationRequest notificationRequest) {
        int seconds = ThreadLocalRandom.current().nextInt(2, 11);
        logger.info("##### Request will complete in {}sec for Notification Request {}", seconds, notificationRequest);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep was interrupted.");        }

        String confirmationId = UUID.randomUUID().toString();

        logger.info("##### Notification Reservation {} completed for Order {}", confirmationId, notificationRequest.orderId());

        return confirmationId;
    }
}
