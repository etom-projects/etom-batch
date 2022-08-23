package com.kurly.delivery.batch.batch.domain.product.repository;

import com.kurly.delivery.batch.batch.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
