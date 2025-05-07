package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
@Getter
public class Sellers {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Double rating;
    private String contactEmail;
    private String contactPhone;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Products> productList;

}
