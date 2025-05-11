package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.OptionDto;
import com.hello.ecommerce.entity.ProductOptionGroups;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.repository.ProductOptionGroupsRepository;
import com.hello.ecommerce.repository.ProductOptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductOptionServiceImpl implements ProductOptionService {

    private final ProductOptionsRepository productOptionsRepository;
    private final ProductOptionGroupsRepository productOptionGroupsRepository;

    public ProductOptions selectById(Long id) {
        return productOptionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 옵션이 존재하지 않습니다."));
    }

    public OptionDto save(OptionDto dto) {
        ProductOptionGroups optionGroup = productOptionGroupsRepository.findById(dto.getOptionGroupId())
                .orElseThrow(() -> new RuntimeException("상품 옵션 그룹 정보가 없습니다."));

        ProductOptions entity = dto.toEntity(optionGroup);
        ProductOptions saved = productOptionsRepository.save(entity);

        return new OptionDto(saved);
    }

    public OptionDto update(OptionDto dto) {
        ProductOptions productOptions = productOptionsRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("상품 옵션 정보가 없습니다."));

        productOptions.setName(dto.getName());
        productOptions.setAdditionalPrice(dto.getAdditionalPrice());
        productOptions.setSku(dto.getSku());
        productOptions.setStock(dto.getStock());
        productOptions.setDisplayOrder(dto.getDisplayOrder());

        return new OptionDto(productOptions);
    }

    public void delete(Long id) {
        productOptionsRepository.deleteById(id);
    }
}
