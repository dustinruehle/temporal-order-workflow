package com.dr.sandbox.temporal.activity;

import com.dr.sandbox.temporal.model.InventoryReservationRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface InventoryActivity {
    @ActivityMethod
    void reserveItems(InventoryReservationRequest reservationRequest);
}
