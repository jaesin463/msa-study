package com.xinxe.orderservice.service;

import com.xinxe.orderservice.dto.OrderDto;
import com.xinxe.orderservice.jpa.Order;

public interface OrderService {
  OrderDto createOrder(OrderDto orderDetails);
  OrderDto getOrderByOrderId(String orderId);
  Iterable<Order> getOrdersByUserId(String userId);
}
