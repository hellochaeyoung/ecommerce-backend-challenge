package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_prices")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPrices {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int basePrice;
    private int salePrice;
    private int costPrice;
    private String currency;
    private int taxRate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products product;
}
