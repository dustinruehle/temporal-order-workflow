package com.dr.sandbox.temporal.activityimpl;

import com.dr.sandbox.temporal.activity.ShippingActivity;
import com.dr.sandbox.temporal.model.ShippingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ShippingActivityImpl implements ShippingActivity {
    private static final Logger logger = LoggerFactory.getLogger(ShippingActivityImpl.class);

    @Override
    public String ship(ShippingRequest shippingRequest) {
        int seconds = ThreadLocalRandom.current().nextInt(2, 11);
        logger.info("##### .....Request will complete in {}sec  for Shipping Request {}", seconds, shippingRequest);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep was interrupted.");        }

        String shippingId = UUID.randomUUID().toString();

        logger.info("##### Payment Request {} completed for Order {}", shippingId, shippingRequest.orderId());

        return shippingId;

    }
}
