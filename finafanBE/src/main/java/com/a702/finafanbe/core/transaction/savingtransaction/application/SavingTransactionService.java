package com.a702.finafanbe.core.transaction.savingtransaction.application;

import com.a702.finafanbe.core.transaction.savingtransaction.dto.response.SavingTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingTransactionService {


    public SavingTransactionResponse getSavingAccounts() {
        return new SavingTransactionResponse();
    }
}
