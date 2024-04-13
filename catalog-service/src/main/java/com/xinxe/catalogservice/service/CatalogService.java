package com.xinxe.catalogservice.service;

import com.xinxe.catalogservice.jpa.Catalog;

public interface CatalogService {
  Iterable<Catalog> getAllCatalogs();
}
