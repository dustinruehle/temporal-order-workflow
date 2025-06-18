package com.dr.sandbox.temporal.model;

import java.time.LocalDate;
import java.util.List;

public record ShippingRequest(
        String orderId,
        String customerId,
        Address address,
        List<Item> items,
        String shippingMethod,
        LocalDate requestedShipDate,
        String notes
) {
}