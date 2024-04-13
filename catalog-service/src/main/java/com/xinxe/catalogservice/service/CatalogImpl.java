package com.xinxe.catalogservice.service;

import com.xinxe.catalogservice.jpa.Catalog;
import com.xinxe.catalogservice.jpa.CatalogRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogImpl implements CatalogService{
  private final CatalogRepository catalogRepository;

  @Override
  public Iterable<Catalog> getAllCatalogs() {
    return catalogRepository.findAll();
  }
}
