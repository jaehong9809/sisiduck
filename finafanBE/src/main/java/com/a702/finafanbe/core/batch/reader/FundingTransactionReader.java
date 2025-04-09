package com.a702.finafanbe.core.batch.reader;

import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.funding.entity.FundingTransactionStatus;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FundingTransactionReader implements ItemReader<FundingGroup> {

    private final FundingGroupRepository fundingGroupRepository;
    private List<FundingGroup> fundings;

    private int nextIndex;

    @Override
    public FundingGroup read() throws Exception {

        if (fundings == null) {
            fundings = fundingGroupRepository.findAllByStatus(FundingStatus.SUCCESS);
        }
        if (nextIndex < fundings.size()) {
            return fundings.get(nextIndex++);
        } else {
            return null;
        }
    }
}
