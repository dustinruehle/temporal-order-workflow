package com.dr.sandbox.temporal.activity;

import com.dr.sandbox.temporal.model.ShippingRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ShippingActivity {
    @ActivityMethod
    String ship(ShippingRequest shippingRequest);
}
