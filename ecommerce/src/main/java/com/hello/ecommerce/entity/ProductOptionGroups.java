package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product_option_groups")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductOptionGroups {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @OneToMany(mappedBy = "productOptionGroup", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ProductOptions> productOptions;
}
