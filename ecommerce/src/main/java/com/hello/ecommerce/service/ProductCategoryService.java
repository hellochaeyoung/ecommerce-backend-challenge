package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.*;
import com.hello.ecommerce.dto.req.ProductCategoryReqDto;
import com.hello.ecommerce.dto.res.ProductCategoryResDto;
import com.hello.ecommerce.dto.res.ProductResDto;
import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.entity.ProductCategories;
import com.hello.ecommerce.entity.ProductImages;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {
    ProductCategoryResDto findProductsByCategoryId(ProductCategoryReqDto dto);
}
