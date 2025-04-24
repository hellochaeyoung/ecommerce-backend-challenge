package com.hello.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Categories {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer level;
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    private Categories parent;


}
