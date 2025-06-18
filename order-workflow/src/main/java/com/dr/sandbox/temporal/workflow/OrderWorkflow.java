package com.dr.sandbox.temporal.workflow;

import com.dr.sandbox.temporal.model.Order;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {
    @WorkflowMethod
    void processOrder(Order order);
}
