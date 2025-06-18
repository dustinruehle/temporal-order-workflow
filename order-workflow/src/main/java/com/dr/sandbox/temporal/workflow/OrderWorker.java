package com.dr.sandbox.temporal.workflow;

import com.dr.sandbox.temporal.TaskQueues;
import com.dr.sandbox.temporal.workflow.OrderWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class OrderWorker {
    public static void main(String[] args) {

        WorkflowServiceStubs serviceStub = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(serviceStub);
        WorkerFactory factory = WorkerFactory.newInstance(client);


        Worker orderWorker = factory.newWorker(TaskQueues.ORDER_TASK_QUEUE_NAME);
        orderWorker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);

        factory.start();

    }
}
