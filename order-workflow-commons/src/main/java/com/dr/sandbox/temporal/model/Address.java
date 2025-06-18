package com.dr.sandbox.temporal.model;

public record Address(
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {
}