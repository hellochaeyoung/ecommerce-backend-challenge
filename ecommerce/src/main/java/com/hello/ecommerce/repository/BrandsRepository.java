package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.Brands;
import com.hello.ecommerce.entity.Sellers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Long> {
}
