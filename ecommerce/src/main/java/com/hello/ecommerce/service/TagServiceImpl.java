package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.Tags;
import com.hello.ecommerce.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagsRepository tagsRepository;

    public List<Tags> findAllById(List<Long> tagIdList) {
        return tagsRepository.findAllById(tagIdList);
    }
}
