package com.dr.sandbox.temporal.activityimpl;

import com.dr.sandbox.temporal.activity.PaymentActivity;
import com.dr.sandbox.temporal.model.PaymentDetails;
import com.dr.sandbox.temporal.model.PaymentRequest;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

public class PaymentActivityImpl implements PaymentActivity {
    public static final Logger logger = Workflow.getLogger(PaymentActivityImpl.class);

    @Override
    public void process(PaymentRequest paymentRequest) {
        logger.info("######################################");
        logger.info("##### Payment Details {}", paymentRequest);
        logger.info("######################################");

    }
}
