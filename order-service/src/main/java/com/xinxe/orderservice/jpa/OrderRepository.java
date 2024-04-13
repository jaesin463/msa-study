package com.xinxe.orderservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Order findByOrderId(String orderId);

  Iterable<Order> findByUserId(String userId);
}
