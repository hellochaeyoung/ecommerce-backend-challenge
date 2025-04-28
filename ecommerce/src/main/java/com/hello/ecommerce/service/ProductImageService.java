package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.entity.ProductImages;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductOptionService productOptionService;
    private final ProductImagesRepository productImagesRepository;

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
}
