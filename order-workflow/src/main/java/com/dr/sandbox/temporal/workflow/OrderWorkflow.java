package com.dr.sandbox.temporal.workflow;

import com.dr.sandbox.temporal.model.Order;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {
    @WorkflowMethod
    void processOrder(Order order);

    @SignalMethod
    void cancelOrder(String workflowId);

    @QueryMethod
    String getCurrentStatus();


}
