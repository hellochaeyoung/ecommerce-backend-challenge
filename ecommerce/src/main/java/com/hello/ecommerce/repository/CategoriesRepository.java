package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    List<Categories> findByParentIdIsNull();

}
