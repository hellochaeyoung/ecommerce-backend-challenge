package com.hello.ecommerce.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product_option_groups")
public class ProductOptionGroups {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer displayOrder;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Products> productsList;
}
