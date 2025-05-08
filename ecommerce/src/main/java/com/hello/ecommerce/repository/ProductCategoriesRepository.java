package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.entity.ProductCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoriesRepository extends JpaRepository<ProductCategories, Long> {

    Page<ProductCategories> findByCategoryId(Long categoryId, Pageable pageable);
}
