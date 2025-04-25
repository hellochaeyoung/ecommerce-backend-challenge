package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.ProductOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, Long> {
}
