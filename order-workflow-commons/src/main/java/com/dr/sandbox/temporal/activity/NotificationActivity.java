package com.dr.sandbox.temporal.activity;

import com.dr.sandbox.temporal.model.NotificationRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface NotificationActivity {
    @ActivityMethod
    String sendConfirmation(NotificationRequest notificationRequest);
}
