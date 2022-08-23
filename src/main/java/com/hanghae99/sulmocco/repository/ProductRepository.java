package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
