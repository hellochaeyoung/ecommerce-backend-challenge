package com.hello.ecommerce.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
public class Sellers {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Double rating;

    @OneToMany(mappedBy = "sellers", fetch = FetchType.LAZY)
    private List<Products> productList;

}
