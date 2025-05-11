package com.hello.ecommerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.*;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.req.ProductSearchReqDto;
import com.hello.ecommerce.dto.res.*;
import com.hello.ecommerce.entity.*;
import com.hello.ecommerce.repository.CategoriesRepository;
import com.hello.ecommerce.repository.ProductCategoriesRepository;
import com.hello.ecommerce.repository.ProductsRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;

    private final SellerService sellerService;
    private final BrandService brandService;
    private final ProductOptionService productOptionService;
    private final CategoryService categoryService;
    private final TagService tagService;

    private final ObjectMapper objectMapper;

    public void save(ProductSaveDto dto) throws JsonProcessingException {
        Sellers seller = sellerService.selectById(dto.getSellerId());
        Brands brand = brandService.selectById(dto.getBrandId());

        Products newProducts = Products.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .seller(seller)
                .brand(brand)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(dto.getStatus())
                .build();

        setProductDetail(dto, newProducts);
        setProductPrice(dto, newProducts);
        setProductCategory(dto, newProducts);
        setProductOptionGroup(dto, newProducts);
        setProductImage(dto, newProducts);
        setProductTag(dto, newProducts);

        Products saved = productsRepository.save(newProducts);
        log.info("saved products id: {}", saved.getId());
    }

    private void setProductTag(ProductSaveDto dto, Products newProducts) {
        List<Tags> tags = tagService.findAllById(dto.getTags());
        List<ProductTags> productTags = tags.stream()
                .map(t -> ProductTags.builder().tag(t).build()).toList();
        newProducts.setProductTags(productTags);
    }

    private void setProductImage(ProductSaveDto dto, Products newProducts) {
        List<ProductImages> productImages = dto.getImages().stream().map(image -> {
            if (null != image.getOptionId()) {
                ProductOptions productOption = productOptionService.selectById(image.getOptionId());
                return ProductImages.builder()
                        .url(image.getUrl())
                        .altText(image.getAltText())
                        .isPrimary(image.getIsPrimary())
                        .displayOrder(image.getDisplayOrder())
                        .option(productOption)
                        .build();
            }else {
                return ProductImages.builder()
                        .url(image.getUrl())
                        .altText(image.getAltText())
                        .isPrimary(image.getIsPrimary())
                        .displayOrder(image.getDisplayOrder())
                        .option(null)
                        .build();
            }

        }).toList();
        newProducts.setProductImages(productImages);
    }

    private void setProductOptionGroup(ProductSaveDto dto, Products newProducts) {
        List<ProductOptionGroups> groupList = dto.getOptionGroups().stream().map(og -> {
            ProductOptionGroups group = ProductOptionGroups.builder()
                    .name(og.getName())
                    .displayOrder(og.getDisplayOrder())
                    .build();

            List<ProductOptions> options = og.getOptions().stream().map(OptionDto::toEntity).toList();
            group.setProductOptions(options);

            return group;
        }).toList();
        newProducts.setProductOptionGroups(groupList);
    }

    private void setProductCategory(ProductSaveDto dto, Products newProducts) {
        List<ProductCategories> productCategories = dto.getCategories().stream()
                .map(cg -> {
                    Categories category = categoryService.selectById(cg.getCategoryId());
                    return ProductCategories.builder()
                            .category(category)
                            .isPrimary(cg.getIsPrimary())
                            .build();
                }).toList();
        newProducts.setCategories(productCategories);
    }

    private void setProductPrice(ProductSaveDto dto, Products newProducts) {
        ProductPrices productPrice = dto.getPrice().toEntity();
        newProducts.setProductPrices(productPrice);
    }

    private void setProductDetail(ProductSaveDto dto, Products newProducts) throws JsonProcessingException {
        Map<String, Object> map = objectMapper.convertValue(dto.getDetail().getAdditionalInfo(), new TypeReference<>() {});
        ProductDetails productDetails = dto.getDetail().toEntity(dto.getDetail().getDimensions(), map);
        newProducts.setProductDetails(productDetails);
    }

    public ProductDataResDto selectProductList(ProductListReqDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage()-1, dto.getPerPage());
        Page<ProductResDto> productList = productsRepository.findProductsByCondition(dto, pageable);

        PaginationDto paginationDto = new PaginationDto(productList.getTotalElements(), productList.getTotalPages(), dto.getPage(), dto.getPerPage());

        return new ProductDataResDto(productList.getContent(), paginationDto);
    }

    public ProductDetailResDto selectProductDetail(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("요청한 상품을 찾을 수 없습니다."));

        return ProductDetailResDto.toDto(product);

    }

    public ProductResDto updateProduct(Long id, ProductSaveDto dto) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("요청한 상품을 찾을 수 없습니다."));

        Sellers seller = sellerService.selectById(dto.getSellerId());

        Brands brand = brandService.selectById(dto.getBrandId());

        product.setName(dto.getName());
        product.setSlug(dto.getSlug());
        product.setShortDescription(dto.getShortDescription());
        product.setFullDescription(dto.getFullDescription());
        product.setSeller(seller);
        product.setBrand(brand);
        product.setStatus(dto.getStatus());
        product.getDetails().update(dto.getDetail());
        product.getPrices().update(dto.getPrice());

        // 카테고리 등 추가 필요

        return new ProductResDto(product.getId(), product.getName(), product.getSlug(), product.getUpdatedAt());
    }

    public void deleteProduct(Long id) {
        productsRepository.deleteById(id);
    }

    public ProductSearchResDto findProductsBySearch(ProductSearchReqDto dto) {
        PageImpl<Products> productsBySearch = productsRepository.findProductsBySearch(dto);

        List<ProductResDto> productResDtoList = productsBySearch.getContent().stream().map(product -> {
            ProductImages primaryImage
                    = product.getProductImages().stream().filter(ProductImages::getIsPrimary).toList().get(0);

            return new ProductResDto().builder()
                    .id(product.getId())
                    .name(product.getName())
                    .slug(product.getSlug())
                    .shortDescription(product.getShortDescription())
                    .basePrice(product.getPrices().getBasePrice())
                    .salePrice(product.getPrices().getSalePrice())
                    .currency(product.getPrices().getCurrency())
                    .primaryImage(new ImageDto(primaryImage.getAltText(), primaryImage.getUrl()))
                    .brand(new BrandDto(product.getBrand().getId(), product.getBrand().getName()))
                    .rating(product.getSeller().getRating())
                    .inStock(dto.isInStock())
                    .createdAt(product.getCreatedAt())
                    .build();

        }).toList();

        PaginationDto paginationDto = new PaginationDto(productsBySearch.getTotalElements(), productsBySearch.getTotalPages(), dto.getPage(), productsBySearch.getSize());
        return new ProductSearchResDto(dto.getKeyword(), productsBySearch.getTotalElements(), productResDtoList, paginationDto);
    }
    
    public ProductMainResDto findForMain(int limit) {
        List<Products> allByCreatedAtDesc = productsRepository.findAllByOrderByCreatedAtDesc();
        List<ProductResDto> newProductList = getProductResDtos(allByCreatedAtDesc);


        List<Products> popularProducts = productsRepository.findPopularProducts(limit);
        List<ProductResDto> popularProductResDtoList = getProductResDtos(popularProducts);


        List<Tuple> featuredCategories = productsRepository.findFeaturedCategories(limit);
        List<CategoryDto> featuredCategoriesList = featuredCategories.stream().map(tuple -> {
            Long id = tuple.get(0, Long.class);
            String name = tuple.get(1, String.class);
            String slug = tuple.get(2, String.class);
            String imageUrl = tuple.get(3, String.class);
            Long productCount = tuple.get(4, Long.class);
            return new CategoryDto().builder()
                    .categoryId(id)
                    .name(name)
                    .slug(slug)
                    .imageUrl(imageUrl)
                    .productCount(productCount)
                    .build();
        }).toList();

        return new ProductMainResDto(newProductList, popularProductResDtoList, featuredCategoriesList);
    }

    private List<ProductResDto> getProductResDtos(List<Products> productsList) {
        return productsList.stream().map(product -> {
            List<ProductImages> primaryImage
                    = product.getProductImages().stream().filter(ProductImages::getIsPrimary).toList();

            ImageDto imageDto = !primaryImage.isEmpty()
                    ? new ImageDto(primaryImage.get(0).getAltText(), primaryImage.get(0).getUrl()) : null;
            return new ProductResDto().builder()
                    .id(product.getId())
                    .name(product.getName())
                    .slug(product.getSlug())
                    .shortDescription(product.getShortDescription())
                    .basePrice(product.getPrices().getBasePrice())
                    .salePrice(product.getPrices().getSalePrice())
                    .currency(product.getPrices().getCurrency())
                    .primaryImage(imageDto)
                    .brand(new BrandDto(product.getBrand().getId(), product.getBrand().getName()))
                    .rating(product.getSeller().getRating())
                    .createdAt(product.getCreatedAt())
                    .build();
        }).toList();
    }

}
