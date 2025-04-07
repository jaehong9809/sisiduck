package com.a702.finafanbe.core.batch.config;

import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

        // FundingPendingTransaction fundingPendingTransaction;
        // if : fundingExpiryDate가 지났으면 (NOW() 날짜랑 비교)
        //      if : transaction (deletedAt is not null) 의 총합과 비교해서 그거보다 같거나 크면
        //          펀딩 state Success 로 변환
        //      else : false로 변환
        log.info("*** Scheduling funding state : {} ***" + LocalDateTime.now());
        List<FundingGroup> finishefundings = fundingGroupRepository.findExpiredFunding(LocalDateTime.now(), FundingStatus.INPROGRESS);
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
