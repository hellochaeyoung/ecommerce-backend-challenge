package com.hello.ecommerce.controller;

import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{id}/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ImageDto> saveProductImage(@PathVariable Long id, @RequestBody ImageDto dto) {
        ImageDto saved = productImageService.save(dto);
        return ResponseEntity.ok(saved);
    }
}
