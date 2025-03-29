package com.a702.finafanbe.core.bank.entity.infrastructure;

import com.a702.finafanbe.core.bank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {

}
