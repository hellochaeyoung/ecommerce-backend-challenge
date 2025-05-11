package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.entity.ProductImages;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductImagesRepository;
import com.hello.ecommerce.repository.ProductOptionsRepository;
import com.hello.ecommerce.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductOptionService productOptionService;
    private final ProductImagesRepository productImagesRepository;
    private final ProductsRepository productsRepository;
    private final ProductOptionsRepository productOptionsRepository;

    public ImageDto save(ImageDto dto) {
        Products products = productsRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        ProductOptions productOptions = productOptionsRepository.findById(dto.getOptionId())
                .orElseThrow(() -> new RuntimeException("상품 옵션 정보가 없습니다."));

        ProductImages saved = productImagesRepository.save(dto.toEntity(productOptions, products));

        return new ImageDto(saved);
    }
}
