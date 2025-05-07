package com.hello.ecommerce.entity;

import com.hello.ecommerce.dto.DetailDto;
import com.hello.ecommerce.dto.DimentionsDto;
import com.hello.ecommerce.dto.ProductSaveDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "product_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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

    public void update(DetailDto dto) {
        setWeight(dto.getWeight());
        setDimensions(dto.getDimensions());
        setMaterials(dto.getMaterials());
        setCountryOfOrigin(dto.getCountryOfOrigin());
        setWarrantyInfo(dto.getWarrantyInfo());
        setCareInstructions(dto.getCareInstructions());
        setAdditionalInfo(dto.getAdditionalInfo());
    }

}
