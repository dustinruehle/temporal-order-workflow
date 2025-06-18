package com.dr.sandbox.temporal.activityimpl;

import com.dr.sandbox.temporal.activity.InventoryActivity;
import com.dr.sandbox.temporal.model.InventoryReservationRequest;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryActivityImpl implements InventoryActivity {
    public static final Logger logger = Workflow.getLogger(InventoryActivityImpl.class);

    @Override
    public String reserveItems(InventoryReservationRequest reservationRequest) {
        int seconds = ThreadLocalRandom.current().nextInt(2, 11);
        logger.info("##### Request will complete in {}sec for Inventory Reservation Request {}", seconds, reservationRequest);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep was interrupted.");        }

        String reservationId = UUID.randomUUID().toString();

        logger.info("##### Inventory Reservation {} completed for Order {}", reservationId, reservationRequest.orderId());

        return reservationId;

    }
}
