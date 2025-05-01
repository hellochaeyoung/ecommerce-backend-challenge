package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {

}
