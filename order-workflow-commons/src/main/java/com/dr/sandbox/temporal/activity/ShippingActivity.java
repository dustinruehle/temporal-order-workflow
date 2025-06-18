package com.dr.sandbox.temporal.activity;

import com.dr.sandbox.temporal.model.Item;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import java.util.List;

@ActivityInterface
public interface ShippingActivity {
    @ActivityMethod
    void ship(String address, List<Item> items);
}
