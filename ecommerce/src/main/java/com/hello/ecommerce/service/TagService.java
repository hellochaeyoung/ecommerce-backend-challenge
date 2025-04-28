package com.hello.ecommerce.service;

import com.hello.ecommerce.entity.Tags;
import com.hello.ecommerce.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagsRepository tagsRepository;

    public void saveAll(List<Tags> tagsList) {
        tagsRepository.saveAll(tagsList);
    }

    public List<Tags> findAllById(List<Long> tagIdList) {
        return tagsRepository.findAllById(tagIdList);
    }
}
