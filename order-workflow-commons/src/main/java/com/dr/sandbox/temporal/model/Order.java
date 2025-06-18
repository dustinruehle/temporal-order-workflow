package com.dr.sandbox.temporal.model;

import java.util.List;

public record Order(
        String orderId,
        PaymentDetails paymentDetails,
        List<Item> items,
        String shippingAddress,
        String customerId
) {
}
