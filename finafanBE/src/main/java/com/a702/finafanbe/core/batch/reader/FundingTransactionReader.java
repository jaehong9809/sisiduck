package com.a702.finafanbe.core.batch.reader;

import com.a702.finafanbe.core.batch.entity.Infrastructure.FundingBatchQueryRepository;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.funding.entity.FundingTransactionStatus;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FundingTransactionReader implements ItemReader<FundingPendingTransaction> {

    private final FundingBatchQueryRepository fundingBatchQueryRepository;
    private Iterator<FundingPendingTransaction> fundingIterator;

    @Override
    public FundingPendingTransaction read() {
        if (fundingIterator == null) {
            List<FundingPendingTransaction> transactions = fundingBatchQueryRepository.findSuccessFunding();
            fundingIterator = transactions.iterator();
        }
        return fundingIterator.hasNext() ? fundingIterator.next() : null;
    }
}
