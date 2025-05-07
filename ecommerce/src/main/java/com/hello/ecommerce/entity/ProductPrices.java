package com.hello.ecommerce.entity;

import com.hello.ecommerce.dto.PriceDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_prices")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
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

    public void update(PriceDto dto) {
        setBasePrice(dto.getBasePrice());
        setSalePrice(dto.getSalePrice());
        setCostPrice(dto.getCostPrice());
        setCurrency(dto.getCurrency());
        setTaxRate(dto.getTaxRate());
    }
}
