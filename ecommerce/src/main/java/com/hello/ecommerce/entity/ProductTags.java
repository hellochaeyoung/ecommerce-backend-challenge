package com.hello.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_tags")
public class ProductTags {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Products product;

    @OneToOne(fetch = FetchType.LAZY)
    private Tags tag;
}
