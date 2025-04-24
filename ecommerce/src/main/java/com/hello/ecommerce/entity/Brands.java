package com.hello.ecommerce.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Brands")
public class Brands {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String website;

    @OneToMany(mappedBy = "brands", fetch = FetchType.LAZY)
    private List<Products> products;
}
