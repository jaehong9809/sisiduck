package com.a702.finafanbe.core.entertainer.entity.infrastructure;

import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntertainerSavingsTransactionDetailRepository extends JpaRepository<EntertainerSavingsTransactionDetail, Integer> {
}
