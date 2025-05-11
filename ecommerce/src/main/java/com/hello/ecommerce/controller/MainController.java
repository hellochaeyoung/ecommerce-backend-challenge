package com.hello.ecommerce.controller;

import com.hello.ecommerce.dto.res.ProductMainResDto;
import com.hello.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductMainResDto> selectProductAndCategoryForMain() {
        ProductMainResDto forMain = productService.findForMain(10);
        return ResponseEntity.ok(forMain);
    }
}
