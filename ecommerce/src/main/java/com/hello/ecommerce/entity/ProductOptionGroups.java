package com.hello.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_option_groups")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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

    public void setProductOptions(List<ProductOptions> productOptions) {
        if(this.productOptions == null){
            this.productOptions = new ArrayList<>();
        }
        this.productOptions.addAll(productOptions);
        productOptions.forEach(op -> op.setProductOptionGroup(this));
    }
}
