package com.xinxe.catalogservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinxe.catalogservice.jpa.Catalog;
import com.xinxe.catalogservice.jpa.CatalogRepository;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

  private final CatalogRepository repository;

  @KafkaListener(topics = "example-catalog-topic")
  public void updateQty(String kafkaMessage) {
    log.info("Kafka Message: ->" + kafkaMessage);

    Map<Object, Object> map = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
      });
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }

    Catalog entity = repository.findByProductId((String) map.get("productId"));
    if (entity != null) {
      entity.setStock(entity.getStock() - (Integer) map.get("qty"));
      repository.save(entity);
    }

  }
}