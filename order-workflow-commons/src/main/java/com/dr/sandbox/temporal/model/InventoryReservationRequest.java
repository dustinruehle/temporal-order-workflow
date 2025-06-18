package com.dr.sandbox.temporal.model;

import java.time.Instant;
import java.util.List;

public record InventoryReservationRequest(
        String orderId,
        String customerId,
        List<Item> items
//        Instant reservationTime,
//        Instant expirationTime
) {
}
