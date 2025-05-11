package com.hello.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.ecommerce.dto.BrandDto;
import com.hello.ecommerce.dto.CategoryDto;
import com.hello.ecommerce.dto.ImageDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import com.hello.ecommerce.dto.req.ProductListReqDto;
import com.hello.ecommerce.dto.req.ProductSearchReqDto;
import com.hello.ecommerce.dto.res.*;
import com.hello.ecommerce.entity.*;
import com.hello.ecommerce.repository.BrandsRepository;
import com.hello.ecommerce.repository.CategoriesRepository;
import com.hello.ecommerce.repository.ProductsRepository;
import com.hello.ecommerce.repository.SellersRepository;
import com.hello.ecommerce.repository.custom.ProductCustomRepository;
import com.querydsl.core.Tuple;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SellersRepository sellersRepository;

    @Autowired
    private BrandsRepository brandsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void 상품_저장() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productSave.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        ProductSaveDto productSaveDto = mapper.readValue(inputStream, ProductSaveDto.class);
        productService.save(productSaveDto);

    }

    @Test
    void 상품_목록_조회() {
        ProductListReqDto dto = ProductListReqDto.builder()
                .page(1)
                .perPage(10)
                .sort("created_at:desc")
                .status("ACTIVE")
                .minPrice(10000)
                .maxPrice(1000000)
                .category(new int[]{45})
                .seller(1)
                .brand(1)
                .inStock(true)
                .search("소파")
                .build();

        ProductDataResDto productDataResDto = productService.selectProductList(dto);

        assertThat(productDataResDto).isNotNull();
    }

    @Test
    @Transactional
    void 상품_상세_조회() {
        Long id = 1L;
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        ProductDetailResDto dto = ProductDetailResDto.toDto(product);

        assertThat(dto.getId()).isEqualTo(product.getId());



    }

    @Test
    @Transactional
    @Rollback(value = false)
    void 상품_수정() throws IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("productUpdate.json");
        assertNotNull(inputStream, "JSON 파일을 찾을 수 없습니다!");

        ProductSaveDto dto = objectMapper.readValue(inputStream, ProductSaveDto.class);

        Long id = 1L;
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다."));

        Sellers seller = sellersRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new RuntimeException("셀러 정보가 없습니다."));

        Brands brand = brandsRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("브랜드 정보가 없습니다"));

        product.setName(dto.getName());
        product.setSlug(dto.getSlug());
        product.setShortDescription(dto.getShortDescription());
        product.setFullDescription(dto.getFullDescription());
        product.setSeller(seller);
        product.setBrand(brand);
        product.setStatus(dto.getStatus());
        product.getDetails().update(dto.getDetail());
        product.getPrices().update(dto.getPrice());


        List<ProductCategories> categoryList = dto.getCategories().stream().map(cg -> {
            Categories category = categoriesRepository.findById(cg.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("카테고리 정보가 없습니다."));
            return ProductCategories.builder()
                    .category(category)
                    .isPrimary(cg.getIsPrimary())
                    .build();
        }).toList();
        product.setCategories(categoryList);

    }

    @Test
    void 상품_검색_조회() {
        String keyword = "소파";
        int page = 0;
        int perPage = 10;
        String sort = "relevance:desc";
        int[] category = {5,6,8};
        int minPrice = 10000;
        int maxPrice = 1000000;
        int[] brand = {1,2};
        int[] seller = {1,2,3};
        boolean inStock = true;
        double rating = 2.0;

        ProductSearchReqDto dto
                = new ProductSearchReqDto(keyword, page, perPage, sort, category, minPrice, maxPrice, brand, seller, inStock, rating);

        //PageImpl<Products> productsBySearch = productCustomRepository.findProductsBySearch(dto);
        ProductSearchResDto productsBySearch = productService.findProductsBySearch(dto);

        assertThat(productsBySearch.getItems().size()).isGreaterThan(0);


    }

    @Test
    @Transactional
    void 메인_상품_조회() {
        int limit = 10;
        List<Products> allByCreatedAtDesc = productsRepository.findAllByOrderByCreatedAtDesc();
        List<ProductResDto> newProductList = allByCreatedAtDesc.stream().map(product -> {
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


        List<Products> popularProducts = productsRepository.findPopularProducts(limit);
        List<ProductResDto> popularProductResDtoList = popularProducts.stream().map(product -> {
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

        ProductMainResDto productMainResDto = new ProductMainResDto(newProductList, popularProductResDtoList, featuredCategoriesList);

        assertThat(productMainResDto.getNewProducts().size()).isGreaterThan(0);


    }

}