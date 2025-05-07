package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "brands")
@Getter
public class Brands {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String website;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Products> products;
}
