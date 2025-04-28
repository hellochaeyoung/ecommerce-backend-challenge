package com.hello.ecommerce.service;

import com.hello.ecommerce.dto.OptionGroupDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.entity.ProductOptionGroups;
import com.hello.ecommerce.entity.ProductOptions;
import com.hello.ecommerce.entity.Products;
import com.hello.ecommerce.repository.ProductOptionGroupsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionGroupService {

    private final ProductOptionGroupsRepository productOptionGroupsRepository;
    private final ProductOptionService productOptionService;

    public void saveAll(List<OptionGroupDto> optionGroupDtoList, Products saved) {
        optionGroupDtoList.forEach(og -> {
                    ProductOptionGroups group = ProductOptionGroups.builder()
                            .name(og.getName())
                            .displayOrder(og.getDisplayOrder())
                            .products(saved)
                            .build();
                    ProductOptionGroups savedGroup = productOptionGroupsRepository.save(group);
                    productOptionService.saveAll(og.getOptions(), savedGroup);
                });
    }
}
