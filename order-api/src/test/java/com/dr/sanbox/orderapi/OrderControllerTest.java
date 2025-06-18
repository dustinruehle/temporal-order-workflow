package com.dr.sanbox.orderapi;

import com.dr.sandbox.orderapi.OrderController;
import com.dr.sandbox.orderapi.OrderRequest;
import com.dr.sandbox.temporal.model.Address;
import com.dr.sandbox.temporal.model.Item;
import com.dr.sandbox.temporal.model.Order;
import com.dr.sandbox.temporal.model.PaymentDetails;
import com.dr.sandbox.temporal.workflow.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

//    private MockMvc mockMvc;
//
//    @Mock
//    private WorkflowClient workflowClient;
//
//    @Mock
//    private OrderWorkflow orderWorkflow;
//
//    private OrderController orderController;
//
//    @BeforeEach
//    void setUp() {
//        orderController = new OrderController(workflowClient);
//        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
//    }
//
//    @Test
//    void createOrder_ShouldStartWorkflowAndReturnWorkflowId() throws Exception {
//        // Arrange
//        String customerId = "customer123";
//        PaymentDetails paymentDetails = new PaymentDetails("4111111111111111", "12/25", "123");
//        Address shippingAddress = new Address("123 Main St", "Apt 4B", "NY", "10001",  "USA");
//        List<Item> items = List.of(new Item("item1", 2));
//
//        OrderRequest orderRequest = new OrderRequest(paymentDetails, items, shippingAddress.toString(), customerId);
//        String requestBody = """
//            {
//                "paymentDetails": {
//                    "cardNumber": "4111111111111111",
//                    "expiryDate": "12/25",
//                    "cvv": "123"
//                },
//                "items": [
//                    {
//                        "itemId": "item1",
//                        "quantity": 2,
//                        "price": 19.99
//                    }
//                ],
//                "shippingAddress": {
//                    "street": "123 Main St",
//                    "unit": "Apt 4B",
//                    "city": "New York",
//                    "state": "NY",
//                    "zipCode": "10001",
//                    "country": "USA"
//                },
//                "customerId": "customer123"
//            }
//            """;
//
//        when(workflowClient.newWorkflowStub(eq(OrderWorkflow.class), any(WorkflowOptions.class)))
//                .thenReturn(orderWorkflow);
//
//        // Act & Assert
//        mockMvc.perform(post("/orders")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isOk());
//
//        // Verify workflow client interactions
//        ArgumentCaptor<WorkflowOptions> optionsCaptor = ArgumentCaptor.forClass(WorkflowOptions.class);
//        verify(workflowClient).newWorkflowStub(eq(OrderWorkflow.class), optionsCaptor.capture());
//
//        WorkflowOptions capturedOptions = optionsCaptor.getValue();
//        assertTrue(capturedOptions.getTaskQueue().isPresent());
//        assertEquals("ORDER_TASK_QUEUE", capturedOptions.getTaskQueue().get());
//        assertTrue(capturedOptions.getWorkflowId().isPresent());
//        assertTrue(capturedOptions.getWorkflowId().get().startsWith("ORDER_"));
//
//        // Verify the workflow was started
//        verify(workflowClient).start(any(), any(Order.class));
//    }
//
//    @Test
//    void createOrder_ShouldCreateOrderWithCorrectData() throws Exception {
//        // Arrange
//        String customerId = "customer123";
//        PaymentDetails paymentDetails = new PaymentDetails("4111111111111111", "12/25", "123");
//        Address shippingAddress = new Address("123 Main St", "Apt 4B", "New York", "NY", "10001", "USA");
//        List<Item> items = List.of(new Item("item1", 2, 19.99));
//
//        OrderRequest orderRequest = new OrderRequest(paymentDetails, items, shippingAddress, customerId);
//
//        when(workflowClient.newWorkflowStub(eq(OrderWorkflow.class), any(WorkflowOptions.class)))
//                .thenReturn(orderWorkflow);
//
//        // Act
//        String workflowId = orderController.createOrder(orderRequest);
//
//        // Assert
//        Assertions.assertNotNull(workflowId);
//        assertTrue(workflowId.startsWith("ORDER_"));
//
//        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
//        verify(workflowClient).start(any(), orderCaptor.capture());
//
//        Order capturedOrder = orderCaptor.getValue();
//        assertEquals(customerId, capturedOrder.customerId());
//        assertEquals(paymentDetails, capturedOrder.paymentDetails());
//        assertEquals(shippingAddress, capturedOrder.shippingAddress());
//        assertEquals(items, capturedOrder.items());
//        Assertions.assertNotNull(capturedOrder.orderId());
//    }
//
//    @Test
//    void createOrder_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        String invalidRequestBody = """
//            {
//                "customerId": "customer123"
//            }
//            """;
//
//        // Act & Assert
//        mockMvc.perform(post("/orders")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidRequestBody))
//                .andExpect(status().isBadRequest());
//    }
}
