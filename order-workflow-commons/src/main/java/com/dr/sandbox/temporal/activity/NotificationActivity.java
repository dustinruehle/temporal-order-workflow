package com.dr.sandbox.temporal.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface NotificationActivity {
    @ActivityMethod
    void sendConfirmation(String customerId);
}
