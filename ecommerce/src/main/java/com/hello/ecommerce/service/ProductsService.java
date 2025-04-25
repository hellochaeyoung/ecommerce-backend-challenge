package com.hello.ecommerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.OptionDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.entity.*;
import com.hello.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final SellersRepository sellersRepository;
    private final BrandsRepository brandsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductOptionsRepository productOptionsRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductPricesRepository productPricesRepository;
    private final ProductOptionGroupsRepository productOptionGroupsRepository;
    private final ProductImagesRepository productImagesRepository;
    private final TagsRepository tagsRepository;
    private final ProductTagsRepository productTagsRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProductCategoriesRepository productCategoriesRepository;

    void save(ProductSaveDto dto) throws JsonProcessingException {
        Optional<Sellers> seller = sellersRepository.findById(dto.getSellerId());
        Optional<Brands> brand = brandsRepository.findById(dto.getBrandId());

        Products newProducts = Products.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .seller(seller.get())
                .brand(brand.get())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(dto.getStatus())
                .build();

        Products saved = productsRepository.save(newProducts);
        log.info("saved products id: {}", saved.getId());

        String dimension = objectMapper.writeValueAsString(dto.getDetail().getDimensions());
        String additionalInfo = objectMapper.writeValueAsString(dto.getDetail().getAdditionalInfo());
        ProductDetails productDetails = dto.getDetail().toEntity(dimension, additionalInfo, saved);
        productDetailsRepository.save(productDetails);

        ProductPrices productPrice = dto.getPrice().toEntity(saved);
        productPricesRepository.save(productPrice);


        List<ProductCategories> productCategories = dto.getCategories().stream()
                .map(cg -> {
                    Optional<Categories> category = categoriesRepository.findById(cg.getCategoryId());
                    return ProductCategories.builder()
                            .category(category.get())
                            .isPrimary(cg.getIsPrimary())
                            .products(saved)
                            .build();
                }).toList();
        productCategoriesRepository.saveAll(productCategories);

        List<ProductOptionGroups> productOptionGroups = dto.getOptionGroups().stream()
                .map(og -> {
                    List<ProductOptions> options = og.getOptions().stream().map(OptionDto::toEntity).toList();
                    productOptionsRepository.saveAll(options);
                    return ProductOptionGroups.builder()
                            .name(og.getName())
                            .displayOrder(og.getDisplayOrder())
                            .productOptions(options)
                            .products(saved)
                            .build();
                }).toList();
        productOptionGroupsRepository.saveAll(productOptionGroups);



        List<ProductImages> productImages = dto.getImages().stream().map(image -> {
            if (null != image.getOptionId()) {
                Optional<ProductOptions> productOption = productOptionsRepository.findById(image.getOptionId());
                return ProductImages.builder()
                        .url(image.getUrl())
                        .altText(image.getAltText())
                        .isPrimary(image.getIsPrimary())
                        .displayOrder(image.getDisplayOrder())
                        .option(productOption.get())
                        .products(saved)
                        .build();
            }else {
                return ProductImages.builder()
                        .url(image.getUrl())
                        .altText(image.getAltText())
                        .isPrimary(image.getIsPrimary())
                        .displayOrder(image.getDisplayOrder())
                        .option(null)
                        .products(saved)
                        .build();
            }

        }).toList();
        productImagesRepository.saveAll(productImages);


        List<Tags> tags = tagsRepository.findAllById(dto.getTags());
        List<ProductTags> productTags = tags.stream()
                .map(t -> ProductTags.builder().tag(t).products(saved).build()).toList();
        tagsRepository.saveAll(tags);
        productTagsRepository.saveAll(productTags);


    }
}
