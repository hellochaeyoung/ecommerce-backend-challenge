package com.hello.ecommerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.req.ReviewReqDto;
import com.hello.ecommerce.dto.req.ReviewSaveReqDto;
import com.hello.ecommerce.dto.res.*;
import com.hello.ecommerce.service.ProductService;
import com.hello.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final ReviewService reviewService;

    @PostMapping
    public void saveProduct(@RequestBody @Validated ProductSaveDto dto) throws JsonProcessingException {
        productService.save(dto);
    }

    @GetMapping
    public ResponseEntity<ProductDataResDto> selectProductList(@RequestParam int page,
                                  @RequestParam int perPage,
                                  @RequestParam String sort,
                                  @RequestParam String status,
                                  @RequestParam int minPrice,
                                  @RequestParam int maxPrice,
                                  @RequestParam int[] category,
                                  @RequestParam int seller,
                                  @RequestParam int brand,
                                  @RequestParam boolean inStock,
                                  @RequestParam String search) {
        ProductListReqDto dto
                = new ProductListReqDto(page, perPage, sort, status, minPrice, maxPrice, category, seller, brand, inStock, search);
        ProductDataResDto productDataResDto = productService.selectProductList(dto);
        return ResponseEntity.ok(productDataResDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResDto> selectProductDetail(@PathVariable Long id) {
        ProductDetailResDto productDetailResDto = productService.selectProductDetail(id);
        return ResponseEntity.ok(productDetailResDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResDto> updateProduct(@PathVariable Long id, @RequestBody ProductSaveDto dto) {
        ProductResDto productResDto = productService.updateProduct(id, dto);
        return ResponseEntity.ok(productResDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<ReviewResDto> selectReviewList(@PathVariable Long id,
                                                         @RequestParam int page,
                                                         @RequestParam int perPage,
                                                         @RequestParam String sort,
                                                         @RequestParam int rating) {
        ReviewReqDto dto = new ReviewReqDto(id, page, perPage, sort, rating);
        ReviewResDto reviewsByCondition = reviewService.findReviewsByCondition(dto);
        return ResponseEntity.ok(reviewsByCondition);
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewDetailResDto> saveReview(@PathVariable Long id, @RequestBody ReviewSaveReqDto dto) {
        dto.setProductId(id);
        ReviewDetailResDto saved = reviewService.save(dto);
        return ResponseEntity.ok(saved);
    }
}
