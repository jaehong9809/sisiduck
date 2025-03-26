package com.a702.finafanbe.core.product.entity.infrastructure;

import com.a702.finafanbe.core.product.entity.AccountProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountProductRepository extends JpaRepository<AccountProduct,Long> {
    AccountProduct findByProductName(String productName);
}
