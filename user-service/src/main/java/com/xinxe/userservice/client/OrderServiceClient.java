package com.xinxe.userservice.client;

import com.xinxe.userservice.vo.ResponseOrder;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="order-service")
public interface OrderServiceClient {

  @GetMapping("/order-service/{userId}/orders")
  List<ResponseOrder> getOrders(@PathVariable String userId);
}
