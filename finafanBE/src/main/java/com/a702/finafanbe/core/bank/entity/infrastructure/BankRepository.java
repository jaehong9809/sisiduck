package com.a702.finafanbe.core.bank.entity.infrastructure;

import com.a702.finafanbe.core.bank.entity.Bank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {

    Optional<Bank> findByBankId(Long bankId);

    Optional<Bank> findByBankCode(String code);
}
