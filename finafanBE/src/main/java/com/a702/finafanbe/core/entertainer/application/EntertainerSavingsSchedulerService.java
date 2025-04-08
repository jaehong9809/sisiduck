package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.ranking.application.RankingService;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntertainerSavingsSchedulerService {

    private final EntertainerSavingsAccountRepository savingsAccountRepository;
    private final InquireDemandDepositAccountService accountService;
    private final DemandDepositFacade demandDepositFacade;
    private final RankingService rankingService;

    /**
     * 매일 자정에 실행되어 만기된 연예인 적금 계좌를 확인하고 이자와 함께 출금계좌로 이체합니다.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void processMaturedAccounts() {

        LocalDateTime now = LocalDateTime.now();
        List<EntertainerSavingsAccount> accounts = savingsAccountRepository.findAll();

        for (EntertainerSavingsAccount account : accounts) {
            try {
                // 계좌 생성일 + 기간(월)이 현재보다 이전이면 만기
                LocalDateTime maturityDate = account.getCreatedAt().plus(account.getDuration(), ChronoUnit.MONTHS);

                if (now.isAfter(maturityDate)) {
                    log.info("만기된 연예인 적금 계좌 발견: ID={}, 계좌번호={}", account.getId(), account.getAccountNo());
                    processMaturityTransfer(account);
                }
            } catch (Exception e) {
                log.error("계좌 ID {} 만기 처리 중 오류 발생: {}", account.getId(), e.getMessage(), e);
            }
        }

        log.info("연예인 적금 만기 계좌 처리 스케줄러 완료");
    }

    /**
     * 만기 적금 계좌의 이체를 처리합니다.
     * 1. 원금+이자를 출금계좌로 이체
     * 2. 적금 계좌 상태 업데이트
     */
    @Transactional
    public void processMaturityTransfer(EntertainerSavingsAccount savingsAccount) {
        log.info("연예인 적금 계좌 만기 이체 처리: ID={}", savingsAccount.getId());

        Account withdrawalAccount = accountService.findAccountById(savingsAccount.getWithdrawalAccountId());

        BigDecimal principal = savingsAccount.getAmount();
        BigDecimal interestRate = BigDecimal.valueOf(savingsAccount.getInterestRate());
        BigDecimal durationInYears = BigDecimal.valueOf(savingsAccount.getDuration()).divide(BigDecimal.valueOf(12));

        // 단리 이자 계산: 원금 * 이율 * 기간(년)
        BigDecimal interest = principal.multiply(interestRate).multiply(durationInYears);
        BigDecimal totalAmount = principal.add(interest);

        log.info("만기 이체 정보: 원금={}, 이자율={}, 기간(월)={}, 산출 이자={}, 총액={}",
                principal, interestRate, savingsAccount.getDuration(), interest, totalAmount);

        demandDepositFacade.transferEntertainerAccount(
                savingsAccount.getId(),
                totalAmount.longValue()
        );

        rankingService.updateRanking(
                savingsAccount.getUserId(),
                savingsAccount.getEntertainerId(),
                principal.negate().doubleValue()
        );

        savingsAccountRepository.deleteById(savingsAccount.getId());

        log.info("연예인 적금 계좌 만기 이체 완료: ID={}, 총 이체금액={}, 출금계좌={}",
                savingsAccount.getId(), totalAmount, withdrawalAccount.getAccountNo());
    }

    /**
     * 적금 계좌를 중도해지하고 이체를 처리합니다.
     * 1. 중도해지 이자 계산 (만기 이자의 특정 비율)
     * 2. 원금+이자를 출금계좌로 이체
     * 3. 적금 계좌 상태 업데이트
     */
    @Transactional
    public void processEarlyTermination(Long savingsAccountId, String terminationReason) {
        log.info("연예인 적금 계좌 중도해지 처리: ID={}, 사유={}", savingsAccountId, terminationReason);

        EntertainerSavingsAccount savingsAccount = savingsAccountRepository.findById(savingsAccountId)
                .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NOT_FOUND_ACCOUNT)));

        Account withdrawalAccount = accountService.findAccountById(savingsAccount.getWithdrawalAccountId());

        // 계약 기간과 실제 보유 기간 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = savingsAccount.getCreatedAt();
        long contractedMonths = savingsAccount.getDuration();
        long actualMonths = ChronoUnit.MONTHS.between(createdAt, now);

        // 중도해지 이자율 계산 (보유 기간에 비례하여 증가, 최대 원래 이자의 100%)
        double termRatio = Math.min(1.0, (double) actualMonths / contractedMonths);
        double earlyTerminationRate = 1 * termRatio; // 최대 원래 이자의 100%까지 지급

        BigDecimal principal = savingsAccount.getAmount();
        BigDecimal contractedInterestRate = BigDecimal.valueOf(savingsAccount.getInterestRate());
        BigDecimal actualYears = BigDecimal.valueOf(actualMonths).divide(BigDecimal.valueOf(12));
        BigDecimal effectiveInterestRate = contractedInterestRate.multiply(BigDecimal.valueOf(earlyTerminationRate));

        // 단리 이자 계산: 원금 * (조정된 이자율) * 실제보유기간(년)
        BigDecimal interest = principal.multiply(effectiveInterestRate).multiply(actualYears);
        BigDecimal totalAmount = principal.add(interest);

        log.info("중도해지 이체 정보: 원금={}, 계약이자율={}, 중도해지이자율={}, 계약기간(월)={}, 실제기간(월)={}, 산출이자={}, 총액={}",
                principal, contractedInterestRate, effectiveInterestRate, contractedMonths, actualMonths, interest, totalAmount);

        demandDepositFacade.transferEntertainerAccount(
                savingsAccount.getId(),
                totalAmount.longValue()
        );

        rankingService.updateRanking(
                savingsAccount.getUserId(),
                savingsAccount.getEntertainerId(),
                principal.negate().doubleValue()
        );

        savingsAccountRepository.deleteById(savingsAccount.getId());

        log.info("연예인 적금 계좌 중도해지 이체 완료: ID={}, 총 이체금액={}, 출금계좌={}",
                savingsAccount.getId(), totalAmount, withdrawalAccount.getAccountNo());
    }

}