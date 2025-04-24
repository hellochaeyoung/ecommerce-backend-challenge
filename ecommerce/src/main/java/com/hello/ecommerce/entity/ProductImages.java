package com.hello.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImages {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOptionGroups productOptionGroup;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductOptions option;
}
