package com.hello.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_categories")
public class ProductCategories {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean isPrimary;

    @OneToOne(fetch = FetchType.LAZY)
    private Products product;

    @OneToOne(fetch = FetchType.LAZY)
    private Categories category;
}
