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

public interface ProductOptionService {
    ProductOptions selectById(Long id);

    OptionDto save(OptionDto dto);

    OptionDto update(OptionDto dto);

    void delete(Long id);
}
