package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "categories")
@Getter
public class Categories {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer level;
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    private Categories parent;


}
