package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private ProductPrices prices;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private ProductDetails details;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductTags> productTags;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductCategories> categories;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductImages> productImages;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductOptionGroups> productOptionGroups;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reviews> reviewsList;

}
