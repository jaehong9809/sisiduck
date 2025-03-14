package com.a702.finafanbe.core.transaction.deposittransaction.application;

import com.a702.finafanbe.core.transaction.deposittransaction.dto.response.DepositTransactionResponse;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure.DepositTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositTransactionService {

    private final DepositTransactionRepository transactionRepository;

    public DepositTransactionResponse getSavingAccounts() {
        return new DepositTransactionResponse();
    }
}
