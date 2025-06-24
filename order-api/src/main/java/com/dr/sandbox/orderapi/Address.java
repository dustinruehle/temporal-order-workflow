package com.dr.sandbox.orderapi;

public record Address(
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {
}