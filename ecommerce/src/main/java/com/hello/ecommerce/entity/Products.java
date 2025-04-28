package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private ProductPrices prices;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private ProductDetails details;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ProductTags> productTags;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ProductCategories> categories;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ProductImages> productImages;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ProductOptionGroups> productOptionGroups;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Reviews> reviewsList;

}
