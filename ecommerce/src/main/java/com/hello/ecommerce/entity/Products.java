package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Products {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sellers seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brands brand;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private ProductPrices prices;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private ProductDetails details;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ProductTags> productTags;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ProductCategories> categories;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ProductImages> productImages;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ProductOptionGroups> productOptionGroups;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<Reviews> reviewsList;

    public void setProductPrices(ProductPrices productPrices) {
        prices = productPrices;
        productPrices.setProduct(this);
    }

    public void setProductDetails(ProductDetails productDetails) {
        details = productDetails;
        productDetails.setProduct(this);
    }

    public void setProductTags(List<ProductTags> productTags) {
        if(this.productTags == null) {
            this.productTags = new ArrayList<>();
        }
        this.productTags.addAll(productTags);
        productTags.forEach(tag -> tag.setProducts(this));
    }

    public void setCategories(List<ProductCategories> categories) {
        // 리스트 자체를 교체하면 안됨,
        // 부모가 자식 엔티티 전체 교체하면 하이버네이트가 연관된 자식 엔티티 삭제하려 하려 했으나 실제로 불가능한 경우 orphan deletion 오류가 발생
        if(this.categories == null) {
            this.categories = new ArrayList<>();
        }
        List<ProductCategories> filteredCategory =
                categories.stream().filter(cg ->
                        this.categories.stream().noneMatch(category -> category.getCategory().getId().equals(cg.getCategory().getId()))).toList();
        this.categories.addAll(filteredCategory);
        categories.forEach(c -> c.setProducts(this));
    }

    public void setProductImages(List<ProductImages> productImages) {
        if(this.productImages == null) {
            this.productImages = new ArrayList<>();
        }
        this.productImages.addAll(productImages);
        productImages.forEach(image -> image.setProducts(this));
    }

    public void setProductOptionGroups(List<ProductOptionGroups> productOptionGroups) {
        if(this.productOptionGroups == null) {
            this.productOptionGroups = new ArrayList<>();
        }
        this.productOptionGroups.addAll(productOptionGroups);
        productOptionGroups.forEach(og -> og.setProducts(this));
    }

}
