package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "product_details")
public class ProductDetails {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double weight;

    @JdbcTypeCode(SqlTypes.JSON)
    private String dimensions;
    private String materials;
    private String countryOfOrigin;
    private String careInstructions;
    @JdbcTypeCode(SqlTypes.JSON)
    private String additionalInfo;

    @OneToOne(fetch = FetchType.LAZY)
    private Products product;

    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY)
    private List<Reviews> reviewsList;

}
