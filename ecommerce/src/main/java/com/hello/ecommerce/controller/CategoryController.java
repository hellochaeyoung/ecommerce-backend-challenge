package com.hello.ecommerce.controller;

import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.dto.req.ProductCategoryReqDto;
import com.hello.ecommerce.dto.res.ProductCategoryResDto;
import com.hello.ecommerce.service.CategoryService;
import com.hello.ecommerce.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> selectCategoryList(@RequestParam int level) {
        List<CategoryDto> categoryDtos = categoryService.selectAll();
        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ProductCategoryResDto> selectProductsByCategory(@PathVariable Long id,
                                                                          @RequestParam int page,
                                                                          @RequestParam int perPage,
                                                                          @RequestParam String sort,
                                                                          @RequestParam boolean includeSubcategories) {
        ProductCategoryReqDto dto = new ProductCategoryReqDto(id, page, perPage, sort, includeSubcategories);
        ProductCategoryResDto productsByCategory = productCategoryService.findProductsByCategoryId(dto);
        return ResponseEntity.ok(productsByCategory);
    }
}
