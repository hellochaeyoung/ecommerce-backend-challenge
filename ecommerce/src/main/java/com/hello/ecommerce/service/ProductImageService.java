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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductOptionService productOptionService;
    private final ProductImagesRepository productImagesRepository;
    private final ProductsRepository productsRepository;
    private final ProductOptionsRepository productOptionsRepository;

    public void saveAll(List<ImageDto> imageDtoList, Products saved) {
        List<ProductImages> productImages = imageDtoList.stream().map(image -> {
            if (null != image.getOptionId()) {
                ProductOptions productOption = productOptionService.selectById(image.getOptionId());
                return ProductImages.builder()
                        .url(image.getUrl())
                        .altText(image.getAltText())
                        .isPrimary(image.getIsPrimary())
                        .displayOrder(image.getDisplayOrder())
                        .option(productOption)
                        .products(saved)
                        .build();
            }else {
                return ProductImages.builder()
                        .url(image.getUrl())
                        .altText(image.getAltText())
                        .isPrimary(image.getIsPrimary())
                        .displayOrder(image.getDisplayOrder())
                        .option(null)
                        .products(saved)
                        .build();
            }

        }).toList();
        productImagesRepository.saveAll(productImages);
    }

    public ImageDto save(ImageDto dto) {
        Products products = productsRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        ProductOptions productOptions = productOptionsRepository.findById(dto.getOptionId())
                .orElseThrow(() -> new RuntimeException("상품 옵션 정보가 없습니다."));

        ProductImages saved = productImagesRepository.save(dto.toEntity(productOptions, products));

        return new ImageDto(saved);
    }
}
