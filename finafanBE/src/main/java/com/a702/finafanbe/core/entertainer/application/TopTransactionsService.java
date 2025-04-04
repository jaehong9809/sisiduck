package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.entertainer.dto.response.TopTransactionResponse;
import com.a702.finafanbe.core.entertainer.dto.response.TopTransactionsResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerRepository;
import com.a702.finafanbe.core.ranking.application.RankingService;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.exception.NotFoundException;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopTransactionsService {

    private final EntertainerRepository entertainerRepository;
    private final EntertainerSavingsAccountRepository savingsAccountRepository;
    private final EntertainerSavingsTransactionDetailRepository transactionDetailRepository;
    private final UserRepository userRepository;
    private final RankingService rankingService;

    private static final int TOP_TRANSACTIONS_LIMIT = 10;

    @Transactional
    public TopTransactionsResponse getTopTransactions(Long entertainerId, String period) {
        Entertainer entertainer = entertainerRepository.findById(entertainerId)
            .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundEntertainer)));

        LocalDateTime filterFromTime = getFilterTimeByPeriod(period);

        List<RankingService.EntertainerRankingEntry> entertainerRanking = new ArrayList<>();
        if(period.equals("weekly")) {
            entertainerRanking = rankingService.getWeeklyEntertainerRanking();
        }else if (period.equals("daily")){
            entertainerRanking = rankingService.getDailyEntertainerRanking();
        }else if (period.equals("total")){
            entertainerRanking = rankingService.getTotalEntertainerRanking();
        }

        RankingService.EntertainerRankingEntry entertainerRankingEntry = entertainerRanking.stream()
                .filter(ranking -> entertainer.getEntertainerId().equals(ranking.getEntertainerId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ResponseData.createResponse(ErrorCode.NotFoundEntertainer)));

        List<EntertainerSavingsAccount> savingsAccounts =
            savingsAccountRepository.findByEntertainerId(entertainerId);

        List<Long> depositAccountIds = savingsAccounts.stream()
            .map(EntertainerSavingsAccount::getDepositAccountId)
            .collect(Collectors.toList());

        if (depositAccountIds.isEmpty()) {
            return TopTransactionsResponse.of(
                    entertainerRankingEntry.getRank(),
                    entertainer,
                    entertainerRankingEntry.getTotalAmount(),
                    List.of()
            );
        }

        List<EntertainerSavingsTransactionDetail> allTransactions = depositAccountIds.stream()
            .flatMap(accountId -> {
                List<EntertainerSavingsTransactionDetail> details =
                    transactionDetailRepository.findByDepositAccountId(accountId)
                        .orElse(List.of());
                return details.stream();
            })
            .collect(Collectors.toList());

        List<EntertainerSavingsTransactionDetail> filteredTransactions;
        if (!"total".equals(period)) {
            filteredTransactions = allTransactions.stream()
                .filter(transaction -> transaction.getCreatedAt().isAfter(filterFromTime))
                .collect(Collectors.toList());
        } else {
            filteredTransactions = allTransactions;
        }

        List<EntertainerSavingsTransactionDetail> topTransactions = filteredTransactions.stream()
            .sorted((t1, t2) -> t2.getTransactionBalance().compareTo(t1.getTransactionBalance()))
            .limit(TOP_TRANSACTIONS_LIMIT)
            .collect(Collectors.toList());

        Map<Long, User> userMap = topTransactions.stream()
            .map(EntertainerSavingsTransactionDetail::getUserId)
            .distinct()
            .map(userId -> userRepository.findById(userId).orElse(null))
            .filter(user -> user != null)
            .collect(Collectors.toMap(User::getUserId, user -> user));

        List<TopTransactionResponse> topTransactionResponses = topTransactions.stream()
            .map(transaction -> {
                User user = userMap.get(transaction.getUserId());
                String userName = user != null ? user.getName() : "Unknown User";

                return TopTransactionResponse.of(
                    transaction.getId(),
                    transaction.getUserId(),
                    userName,
                    transaction.getTransactionBalance(),
                    transaction.getCreatedAt(),
                    transaction.getMessage(),
                    transaction.getImageUrl()
                );
            })
            .collect(Collectors.toList());

        return TopTransactionsResponse.of(
                entertainerRankingEntry.getRank(),
                entertainer,
                entertainerRankingEntry.getTotalAmount(),
                topTransactionResponses
        );
    }

    private LocalDateTime getFilterTimeByPeriod(String period) {
        LocalDate today = LocalDate.now();

        switch (period) {
            case "daily":
                return LocalDateTime.of(today, LocalTime.MIN);

            case "weekly":
                LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                return LocalDateTime.of(monday, LocalTime.MIN);

            case "total":
            default:
                return LocalDateTime.of(1970, 1, 1, 0, 0);
        }
    }
}