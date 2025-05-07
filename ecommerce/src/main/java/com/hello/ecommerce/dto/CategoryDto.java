package com.hello.ecommerce.dto;

import com.hello.ecommerce.entity.Categories;
import com.hello.ecommerce.entity.ProductCategories;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long categoryId;
    private String name;
    private String slug;
    private String description;
    private Boolean isPrimary;
    private int level;
    private String imageUrl;
    private CategoryDto parent;
    private List<CategoryDto> children;

    public CategoryDto(Long categoryId, Boolean isPrimary) {
        this.categoryId = categoryId;
        this.isPrimary = isPrimary;
    }

    public CategoryDto(String slug, String name, Long categoryId) {
        this.slug = slug;
        this.name = name;
        this.categoryId = categoryId;
    }

    public CategoryDto(boolean isPrimary, Categories categories) {
        this.categoryId = categories.getId();
        this.name = categories.getName();
        this.slug = categories.getSlug();
        this.isPrimary = isPrimary;
        this.parent = new CategoryDto(categories.getParent().getSlug(),
                categories.getParent().getName(),
                categories.getParent().getId()
                );
    }

    public CategoryDto(Categories categories, List<CategoryDto> children) {
        this.categoryId = categories.getId();
        this.name = categories.getName();
        this.slug = categories.getSlug();
        this.description = categories.getDescription();
        this.level = categories.getLevel();
        this.imageUrl = categories.getImageUrl();
        this.children = children;
    }

    public static List<CategoryDto> getCategories(List<ProductCategories> productCategories) {
        return productCategories.stream()
                .map(pc -> new CategoryDto(pc.getIsPrimary(), pc.getCategory())).toList();
    }
}
