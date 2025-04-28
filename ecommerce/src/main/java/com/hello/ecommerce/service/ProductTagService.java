package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.ProductTags;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.entity.Tags;
import com.hello.ecommerce.repository.ProductTagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTagService {

    private final ProductTagsRepository productTagsRepository;
    private final TagService tagService;

    public void saveAll(List<Long> tagsList, Products saved) {
        List<Tags> tags = tagService.findAllById(tagsList);
        List<ProductTags> productTags = tags.stream()
                .map(t -> ProductTags.builder().tag(t).products(saved).build()).toList();
        productTagsRepository.saveAll(productTags);
    }
}
