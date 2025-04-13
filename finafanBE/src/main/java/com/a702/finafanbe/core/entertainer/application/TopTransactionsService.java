package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.entertainer.dto.response.TopTransactionResponse;
import com.a702.finafanbe.core.entertainer.dto.response.TopTransactionsResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerRepository;
import com.a702.finafanbe.core.ranking.application.RankingService;
import com.a702.finafanbe.core.ranking.application.RankingService.EntertainerRankingEntry;
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
import org.springframework.data.domain.PageRequest;
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

    @Transactional(readOnly = true)
    public TopTransactionsResponse getTopTransactions(Long entertainerId, String period, PageRequest pageRequest) {
        Entertainer entertainer = entertainerRepository.findById(entertainerId)
            .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundEntertainer)));

        EntertainerRankingEntry entertainerRankingEntry = getRankingEntryByPeriod(period, entertainerId);

        LocalDateTime filterFromTime = getFilterTimeByPeriod(period);

        List<EntertainerSavingsAccount> savingsAccounts =
            savingsAccountRepository.findByEntertainerId(entertainerId);

        List<Long> depositAccountIds = savingsAccounts.stream()
            .map(EntertainerSavingsAccount::getId)
            .collect(Collectors.toList());

        if (depositAccountIds.isEmpty()) {
            return TopTransactionsResponse.of(
                entertainerRankingEntry.getRank(),
                entertainer,
                entertainerRankingEntry.getTotalAmount(),
                List.of()
            );
        }

        List<EntertainerSavingsTransactionDetail> topTransactions =
            transactionDetailRepository.findTopTransactionsByEntertainerIdAndPeriod(
                depositAccountIds,
                filterFromTime,
                "total".equals(period),
                TOP_TRANSACTIONS_LIMIT
            );

        Map<Long, User> userMap = transactionDetailRepository.findUsersByTransactionDetails(topTransactions);

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

    private EntertainerRankingEntry getRankingEntryByPeriod(
        String period,
        Long entertainerId
    ) {
        List<EntertainerRankingEntry> ranking = new ArrayList<>();

        switch (period){
            case "weekly":
                ranking = rankingService.getWeeklyEntertainerRanking();
                break;
            case "daily":
                ranking = rankingService.getDailyEntertainerRanking();
                break;
            case "total":
            default:
                ranking = rankingService.getTotalEntertainerRanking();
                break;
        }

        return ranking.stream()
                .filter(entry -> entertainerId.equals(entry.getEntertainerId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ResponseData.createResponse(ErrorCode.NotFoundEntertainer)));
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