package com.hello.ecommerce.entity;

import com.hello.ecommerce.dto.OptionDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_options")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductOptions {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroups productOptionGroup;
}
