package com.dr.sandbox.temporal.model;

public record PaymentDetails(
        String cardNumber,
        String expiryDate,
        String cvv,
        double amount
) {
}
