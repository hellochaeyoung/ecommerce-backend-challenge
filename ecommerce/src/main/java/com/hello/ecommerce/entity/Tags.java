package com.hello.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Tags")
public class Tags {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String slug;
}
