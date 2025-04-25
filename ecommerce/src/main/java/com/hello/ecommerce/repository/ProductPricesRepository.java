package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.ProductDetails;
import com.hello.ecommerce.entity.ProductPrices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPricesRepository extends JpaRepository<ProductPrices, Long> {
}
