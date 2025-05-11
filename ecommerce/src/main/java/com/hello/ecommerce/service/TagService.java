package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.Tags;
import com.hello.ecommerce.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TagService {
    List<Tags> findAllById(List<Long> tagIdList);
}
