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

public interface ProductImageService {
    ImageDto save(ImageDto dto);
}
