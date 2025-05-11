package com.hello.ecommerce.controller;

import com.hello.ecommerce.dto.req.ProductSearchReqDto;
import com.hello.ecommerce.dto.res.ProductSearchResDto;
import com.hello.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductSearchResDto> search(@RequestParam String keyword,
                                                      @RequestParam int page,
                                                      @RequestParam int perPage,
                                                      @RequestParam String sort,
                                                      @RequestParam int[] category,
                                                      @RequestParam int[] seller,
                                                      @RequestParam int minPrice,
                                                      @RequestParam int maxPrice,
                                                      @RequestParam int[] brand,
                                                      @RequestParam boolean inStock,
                                                      @RequestParam double rating) {
        ProductSearchReqDto dto
                = new ProductSearchReqDto(keyword, page, perPage, sort, category, minPrice, maxPrice, brand, seller, inStock, rating);
        ProductSearchResDto productsBySearch = productService.findProductsBySearch(dto);
        return ResponseEntity.ok(productsBySearch);
    }
}
