package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.custom.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>, ProductCustomRepository {

    List<Products> findAllByOrderByCreatedAtDesc();
}
