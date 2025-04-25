package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.entity.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoriesRepository extends JpaRepository<ProductCategories, Long> {
}
