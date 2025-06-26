package com.dr.sandbox.temporal.activityimpl;

import com.dr.sandbox.temporal.activity.PaymentActivity;
import com.dr.sandbox.temporal.model.PaymentRequest;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PaymentActivityImpl implements PaymentActivity {
    public static final Logger logger = Workflow.getLogger(PaymentActivityImpl.class);

    private int numOfRequests = 0; // used for simulations

    @Override
    public String processPayment(PaymentRequest paymentRequest) {
        int seconds = ThreadLocalRandom.current().nextInt(2, 11);

        numOfRequests++;
        logger.info("################################ Number of Inventory Requests={}", numOfRequests);

        // for requests 4, 5 simulate an error to demo the retry options on the workflow for processing the payment
        if (numOfRequests > 3 && numOfRequests < 6) {
            throw new RuntimeException("################################ Simulated payment failure on request " + numOfRequests);
        }


        logger.info("##### .....Request will complete in {}sec  for Payment Request {}", seconds, paymentRequest);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep was interrupted.");        }

        String paymentId = UUID.randomUUID().toString();

        logger.info("##### Payment Request {} completed for Order {}", paymentId, paymentRequest.orderId());

        return paymentId;

    }
}

