package com.a702.finafanbe.core.transaction.savingtransaction.application;

import com.a702.finafanbe.core.transaction.savingtransaction.dto.response.SavingTransactionResponse;
import com.a702.finafanbe.core.transaction.savingtransaction.entity.infrastructure.SavingTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingTransactionService {

    private final SavingTransactionRepository transactionRepository;

    public SavingTransactionResponse getSavingAccounts() {
        return new SavingTransactionResponse();
    }
}
