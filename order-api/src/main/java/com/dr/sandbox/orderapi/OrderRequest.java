package com.dr.sandbox.orderapi;

import com.dr.sandbox.temporal.model.Item;
import com.dr.sandbox.temporal.model.PaymentDetails;

import java.util.List;

public record OrderRequest(
        PaymentDetails paymentDetails,
        List<Item> items,
        Address shippingAddress,
        String customerId
) {
}