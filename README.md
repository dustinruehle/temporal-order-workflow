# Order Workflow Services with Temporal

This repository demonstrates a **services-based e-commerce order workflow** using [Temporal](https://temporal.io/), structured as a multi-module Java project.

## Key Features

- **Orchestrated Order Workflow** — Java Temporal workflow for end-to-end order processing
- **Separation of Concerns** — Four separate activity workers (payment, inventory, shipping, notification), each as an independent service
- **Spring Boot REST API** — To initiate order workflows over HTTP

---

## Architecture Overview

### Modules

- `order-workflow-commons`
  - Common data classes (`Order`, `Item`, `PaymentDetails` etc)
  - Activity interfaces (`Payment`, `Inventory`, `Shipping`, `Notification`)
  - Task queues — a separate task queue for each activity/worker
- `order-workflow`
  - Workflow interfaces and workflow implementation
  - Order worker
- `payment-worker` — Payment activity implementation and worker main class
- `inventory-worker` — Inventory activity implementation and worker main class
- `shipping-worker` — Shipping activity implementation and worker main class
- `notification-worker` — Notification activity implementation and worker main class
- `order-api` — Spring Boot REST API to start workflows

---

### High Level System Architecture

```mermaid
flowchart TD
    User["User / External System"] -- HTTP Post JSON --> Controller["Spring REST API Controller"]
    Controller -- Start Workflow --> OrderWorker["Order Workflow"]
    OrderWorker --> PaymentActivity["Process Payment"] & InventoryActivity["Reserve Inventory"] & ShippingActivity["Ship Order"] & NotificationActivity["Notify Customer"]
```

### Simple Sequence
**Note**: for each step there is a workflow and activity implementation and the sequence happens for all worker/activities
```mermaid
sequenceDiagram
    participant User
    participant REST_API
    participant Temporal_Server
    participant Workers
    participant ActivityImplementations
    participant OrderWorkflow

    User->>REST_API: HTTP POST /orders 
    REST_API->>Temporal_Server: gRPC call (WorkflowClient.start())
    Temporal_Server-->>REST_API: gRPC response (workflow started)
    REST_API-->>User: HTTP response (workflowId)
    Workers->>Temporal_Server: newWorker (TASK_QUEUE per Activity)
    Temporal_Server-->>Workers: gRPC response (Worker)
    Workers-->>ActivityImplementations: gRPC WorkerFactory.start()
    Workers-->>OrderWorkflow: gRPC WorkerFactory.start()
    OrderWorkflow->>ActivityImplementations: gRPC paymentActivity.processPayment(order)
    ActivityImplementations-->>OrderWorkflow: gRPC response (paymentId)
    OrderWorkflow->>ActivityImplementations: gRPC inventoryActivity.reserveItems(inventoryRequest)
    ActivityImplementations-->>OrderWorkflow: gRPC response (reservationId)
    OrderWorkflow->>ActivityImplementations: gRPC shippingActivity.ship(shippingRequest)
    ActivityImplementations-->>OrderWorkflow: gRPC response (shippingId)
    OrderWorkflow->>ActivityImplementations: gRPC notificationActivity.sendConfirmation(notificationRequest)
    ActivityImplementations-->>OrderWorkflow: gRPC response (confirmationId)
```