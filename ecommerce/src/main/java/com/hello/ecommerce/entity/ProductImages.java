package com.hello.ecommerce.entity;

import com.hello.ecommerce.dto.ImageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_images")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductImages {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOptions option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;
}
