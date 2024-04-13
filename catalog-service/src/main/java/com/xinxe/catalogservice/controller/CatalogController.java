package com.xinxe.catalogservice.controller;

import com.xinxe.catalogservice.jpa.Catalog;
import com.xinxe.catalogservice.service.CatalogService;
import com.xinxe.catalogservice.vo.ResponseCatalog;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
  private final Environment env;
  private final CatalogService catalogService;

  @GetMapping("/health_check")
  public String status(){
    System.out.println("111111111111");
    return String.format("It's Working in Catalog Service on Port %s", env.getProperty("local.server.port"));
  }

  @GetMapping("/catalogs")
  public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
    Iterable<Catalog> catalogList = catalogService.getAllCatalogs();

    List<ResponseCatalog> result = new ArrayList<>();
    catalogList.forEach(v -> {
      result.add(new ModelMapper().map(v, ResponseCatalog.class));
    });

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
