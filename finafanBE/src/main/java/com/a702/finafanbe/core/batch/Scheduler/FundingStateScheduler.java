package com.a702.finafanbe.core.batch.Scheduler;

import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class FundingStateScheduler {

    private final FundingPendingTransactionRepository fundingPendingTransactionRepository;
    private final FundingGroupRepository fundingGroupRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void fundingStateScheduler() {

        log.info("*** Scheduling funding state : {} ***", LocalDateTime.now());
        List<FundingGroup> finishefundings = fundingGroupRepository.findExpiredFunding(LocalDate.now(), FundingStatus.INPROGRESS);
        for (FundingGroup funding : finishefundings) {
            Long sumAmount = fundingPendingTransactionRepository.sumByFundingId(funding.getId());
            if (sumAmount >= funding.getGoalAmount()) {
                funding.updateFundingStatus(FundingStatus.SUCCESS);
            } else {
                funding.updateFundingStatus(FundingStatus.FAILED);
            }
        }
    }
}
