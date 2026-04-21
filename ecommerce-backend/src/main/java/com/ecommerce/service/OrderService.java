package com.ecommerce.service;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Order placeOrder(@Valid OrderRequest orderRequest) {
        // Validate products and stock
        double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderRequest.OrderItemRequest item : orderRequest.getItems()) {
            Product product = productService.getProductById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            double unitPrice = product.getPrice();
            totalAmount += unitPrice * item.getQuantity();
            orderItems.add(new OrderItem(product, item.getQuantity(), unitPrice));
        }

        // Create order
        Order order = new Order(orderRequest.getCustomerId(), totalAmount, orderItems);
        orderItems.forEach(item -> item.setOrder(order));
        Order savedOrder = orderRepository.save(order);

        // Reduce stock
        orderRequest.getItems().forEach(item ->
            productService.reduceStock(item.getProductId(), item.getQuantity())
        );

        // Async processing
        processOrderAsync(savedOrder);

        return savedOrder;
    }

    private void processOrderAsync(Order order) {
        CompletableFuture.runAsync(() -> {
            try {
                // Simulate processing
                Thread.sleep(2000);
                order.setStatus(Order.OrderStatus.CONFIRMED);
                orderRepository.save(order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}