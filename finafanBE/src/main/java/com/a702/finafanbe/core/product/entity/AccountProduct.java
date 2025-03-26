package com.a702.finafanbe.core.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "account_product")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccountProduct {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,name = "product-type")
    private String productType;

    @Column(nullable = false,name = "product_name")
    private String productName;

    @Column(nullable = false, name = "product_unique_no")
    private String productUniqueNo;
}
