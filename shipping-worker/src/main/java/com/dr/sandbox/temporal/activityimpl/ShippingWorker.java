package com.dr.sandbox.temporal.activityimpl;

import com.dr.sandbox.temporal.TaskQueues;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class ShippingWorker {
    public static void main(String[] args) {

        WorkflowServiceStubs serviceStub = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(serviceStub);
        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker paymentWorker = factory.newWorker(TaskQueues.SHIPPING_TASK_QUEUE_NAME);
        paymentWorker.registerActivitiesImplementations(new ShippingActivityImpl());

        factory.start();

    }
}
