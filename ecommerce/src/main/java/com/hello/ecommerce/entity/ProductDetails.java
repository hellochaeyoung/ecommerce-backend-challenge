package com.hello.ecommerce.entity;

import com.hello.ecommerce.dto.DetailDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "product_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double weight;

    @JdbcTypeCode(SqlTypes.JSON)
    private String dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    @JdbcTypeCode(SqlTypes.JSON)
    private String additionalInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products product;

}
