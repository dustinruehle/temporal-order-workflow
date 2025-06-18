package com.dr.sandbox.temporal.model;

import java.util.List;

public record Order(
        PaymentDetails paymentDetails,
        List<Item> items,
        String shippingAddress,
        String customerId,
        String orderId
) {
}
