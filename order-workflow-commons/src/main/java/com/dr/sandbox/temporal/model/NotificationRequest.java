package com.dr.sandbox.temporal.model;

import java.time.Instant;
import java.util.Map;

public record NotificationRequest(
        String notificationId,
        String orderId,
        String customerId,
        String channel,
        String destination,
        String subject,
        String message,
        Instant timestamp,
        Map<String, String> metadata
) {
}
