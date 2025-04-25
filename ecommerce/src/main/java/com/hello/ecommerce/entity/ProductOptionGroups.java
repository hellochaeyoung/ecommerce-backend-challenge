package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product_option_groups")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionGroups {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @OneToMany(mappedBy = "productOptionGroup")
    private List<ProductOptions> productOptions;
}
