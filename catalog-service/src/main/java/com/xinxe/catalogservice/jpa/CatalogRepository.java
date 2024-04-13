package com.xinxe.catalogservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

  Catalog findByProductId(String productId);
}
