package com.a702.finafanbe.core.batch.writer;

import com.a702.finafanbe.core.batch.dto.TransactionResponse;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.FundingTransactionStatus;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class FundingTransactionWriter implements ItemWriter<TransactionResponse> {

    private final FundingPendingTransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public void write(Chunk<? extends TransactionResponse> chunk) throws Exception {
        for (TransactionResponse response : chunk) {
            System.out.println("writer" + response.id());
            transactionRepository.findById(response.id())
                    .ifPresent(tx -> tx.updateStatus(response.status()));
        }
    }
}
