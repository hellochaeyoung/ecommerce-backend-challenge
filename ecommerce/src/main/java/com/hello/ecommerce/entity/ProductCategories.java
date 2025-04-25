package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_categories")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategories {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isPrimary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories category;
}
