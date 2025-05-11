package com.hello.ecommerce.controller;

import com.hello.ecommerce.dto.OptionDto;
import com.hello.ecommerce.service.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{id}/options")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PostMapping
    public ResponseEntity<OptionDto> saveProductOption(@RequestBody OptionDto dto) {
        OptionDto saved = productOptionService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{optionId}")
    public ResponseEntity<OptionDto> updateProductOption(@PathVariable Long optionId, @RequestBody OptionDto dto) {
        dto.setId(optionId);
        OptionDto update = productOptionService.update(dto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{optionId}")
    public void deleteProductOption(@PathVariable Long optionId) {
        productOptionService.delete(optionId);
    }


}
