package com.dr.sandbox.temporal.model;

public record PaymentRequest(
        String orderId,
        String cardNumber,
        String expiryDate,
        String cvv,
        double amount
) {
}
