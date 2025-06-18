package com.dr.sandbox.temporal.activity;

import com.dr.sandbox.temporal.model.PaymentDetails;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentActivity {
    @ActivityMethod
    void process(PaymentDetails paymentDetails);
}
