package com.hello.ecommerce.repository;

import com.hello.ecommerce.entity.ProductTags;
import com.hello.ecommerce.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagsRepository extends JpaRepository<ProductTags, Long> {
}
