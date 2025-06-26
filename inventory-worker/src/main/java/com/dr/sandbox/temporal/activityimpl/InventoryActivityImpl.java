package com.dr.sandbox.temporal.activityimpl;

import com.dr.sandbox.temporal.activity.InventoryActivity;
import com.dr.sandbox.temporal.model.InventoryReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryActivityImpl implements InventoryActivity {
    private static final Logger logger = LoggerFactory.getLogger(InventoryActivityImpl.class);

    private int numOfRequests = 0; // used for simulations

    @Override
    public String reserveItems(InventoryReservationRequest reservationRequest) {
        int seconds = ThreadLocalRandom.current().nextInt(2, 11);

        numOfRequests++;
        logger.info("################################ Number of Inventory Requests={}", numOfRequests);

        // on every 3rd request, simulate a long 30 second human in the loop
        // TODO: add child workflow call to simulate this
        if (numOfRequests % 3 == 0) {
            logger.info("##### Inventory Reservation Request {} requires a human-in-the-loop inventory reservation", reservationRequest);
            try {
                Thread.sleep(60 * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Sleep was interrupted.");        }

        } else {
            logger.info("##### Request will complete in {}sec for Inventory Reservation Request {}", seconds, reservationRequest);
            try {
                Thread.sleep(seconds * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Sleep was interrupted.");        }

        }

        String reservationId = UUID.randomUUID().toString();

        logger.info("##### Inventory Reservation {} completed for Order {}", reservationId, reservationRequest.orderId());

        return reservationId;

    }
}
