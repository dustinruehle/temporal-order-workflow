# Order Workflow Services with Temporal

This repository demonstrates a **services-based e-commerce order workflow** using [Temporal](https://temporal.io/), structured as a multi-module Java project.

## Key Features

- **Orchestrated Order Workflow** — Java Temporal workflow for end-to-end order processing
- **Separation of Concerns** — Four separate activity workers (payment, inventory, shipping, notification), each as an independent service
- **Spring Boot REST API** — To initiate order workflows over HTTP

---

## Architecture Overview

### Modules

- `shared-models` — Common data classes (`Order`, `Item`, `PaymentDetails`)
- `order-workflow` — Workflow and activity interfaces and workflow implementation (no worker logic)
- `payment-worker` — Payment activity implementation and worker main class
- `inventory-worker` — Inventory activity implementation and worker main class
- `shipping-worker` — Shipping activity implementation and worker main class
- `notification-worker` — Notification activity implementation and worker main class
- `order-api` — Spring Boot REST API to start workflows

---

### High Level System Architecture

```mermaid
flowchart LR
    User[User / External System]
    subgraph Application
        Controller[REST API Controller]
        OrderWorkflowClient[Workflow Client]
        WorkerApp[Worker Process]
    end
    subgraph Temporal
        TemporalServer[Temporal Server (Cloud/Self-Hosted)]
    end
    subgraph External
        InventorySystem[Inventory Service]
        PaymentGateway[Payment Gateway]
        ShippingAPI[Shipping Provider API]
        NotificationSystem[Notification System (Email/SMS)]
    end

    User --> Controller
    Controller --> OrderWorkflowClient
    OrderWorkflowClient -->|Start Workflow| TemporalServer
    TemporalServer --> WorkerApp
    WorkerApp -->|Activity: Reserve Inventory| InventorySystem
    WorkerApp -->|Activity: Process Payment| PaymentGateway
    WorkerApp -->|Activity: Ship Order| ShippingAPI
    WorkerApp -->|Activity: Send Notification| NotificationSystem
```
