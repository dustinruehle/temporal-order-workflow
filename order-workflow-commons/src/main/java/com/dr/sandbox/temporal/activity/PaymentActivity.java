package com.dr.sandbox.temporal.activity;

import com.dr.sandbox.temporal.model.PaymentRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentActivity {
    @ActivityMethod
    String process(PaymentRequest paymentRequest);
}
