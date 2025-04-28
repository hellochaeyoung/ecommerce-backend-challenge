package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.OptionDto;
import com.hello.ecommerce.entity.ProductOptionGroups;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.repository.ProductOptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionsRepository productOptionsRepository;

    public List<ProductOptions> saveAll(List<OptionDto> optionDtoList, ProductOptionGroups groups) {
        List<ProductOptions> options = optionDtoList.stream().map(op -> op.toEntity(groups)).toList();
        return productOptionsRepository.saveAll(options);
    }

    public ProductOptions selectById(Long id) {
        return productOptionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 옵션이 존재하지 않습니다."));
    }
}
