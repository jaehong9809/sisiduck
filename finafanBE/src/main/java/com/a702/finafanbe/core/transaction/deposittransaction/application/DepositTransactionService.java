package com.a702.finafanbe.core.transaction.deposittransaction.application;

import com.a702.finafanbe.core.transaction.deposittransaction.dto.response.DepositTransactionResponse;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure.DepositTransactionRepository;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositTransactionService {

    private final EntertainerSavingsTransactionDetailRepository entertainerSavingsTransactionDetailRepository;

    public DepositTransactionResponse getSavingAccounts() {
        return new DepositTransactionResponse();
    }


    public List<EntertainerSavingsTransactionDetail> getEntertainerSavingsDetails(String accountNo) {
       return entertainerSavingsTransactionDetailRepository.findByDepositAccountNo(accountNo);
    }
}
