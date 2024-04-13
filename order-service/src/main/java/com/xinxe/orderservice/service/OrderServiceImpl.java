package com.xinxe.orderservice.service;

import com.xinxe.orderservice.dto.OrderDto;
import com.xinxe.orderservice.jpa.Order;
import com.xinxe.orderservice.jpa.OrderRepository;
import com.xinxe.orderservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public OrderDto createOrder(OrderDto orderDto) {
    orderDto.setOrderId(UUID.randomUUID().toString());
    orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    Order order = mapper.map(orderDto, Order.class);

    orderRepository.save(order);

    OrderDto returnValue = mapper.map(order, OrderDto.class);

    return returnValue;
  }

  @Override
  public OrderDto getOrderByOrderId(String orderId) {
    Order order = orderRepository.findByOrderId(orderId);
    OrderDto orderDto = new ModelMapper().map(order, OrderDto.class);

    return orderDto;
  }

  @Override
  public Iterable<Order> getOrdersByUserId(String userId) {
    return orderRepository.findByUserId(userId);
  }
}
