package com.a702.finafanbe.core.ranking.application;

import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.ranking.dto.response.EnhancedUserRankingResponse;
import com.a702.finafanbe.core.ranking.dto.response.UserRankingResponse;
import com.a702.finafanbe.core.ranking.dto.response.UserTransactionDetailResponse;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTransactionDetailService {

    private final EntertainerSavingsAccountRepository entertainerSavingsAccountRepository;
    private final AccountRepository accountRepository;
    private final EntertainerSavingsTransactionDetailRepository transactionDetailRepository;

    public List<EnhancedUserRankingResponse> getEnhancedUserRankings(
            Long entertainerId,
            List<UserRankingResponse> basicUserRankings) {

        // Find all deposit accounts for this entertainer
        List<EntertainerSavingsAccount> savingsAccounts =
                entertainerSavingsAccountRepository.findByEntertainerId(entertainerId);

        // Map to extract user ID -> deposit account ID mapping
        Map<Long, Long> userToDepositAccountMap = savingsAccounts.stream()
                .collect(Collectors.toMap(
                        EntertainerSavingsAccount::getUserId,
                        EntertainerSavingsAccount::getDepositAccountId,
                        (a, b) -> a // Keep first in case of duplicates
                ));

        // Collect all user IDs from the rankings
        List<Long> userIds = basicUserRankings.stream()
                .map(UserRankingResponse::userId)
                .collect(Collectors.toList());

        // Enhanced result to return
        List<EnhancedUserRankingResponse> result = new ArrayList<>();

        // Process each user in the ranking
        for (UserRankingResponse basicRanking : basicUserRankings) {
            Long userId = basicRanking.userId();
            Long depositAccountId = userToDepositAccountMap.get(userId);

            if (depositAccountId == null) {
                // User has ranking but no deposit account found (shouldn't happen)
                log.warn("User {} convertToUserRankingResponses account for entertainer {}",
                        userId, entertainerId);
                result.add(EnhancedUserRankingResponse.from(
                        basicRanking,
                        List.of() // empty transaction list
                ));
                continue;
            }

            // Get transaction details for this account
            List<UserTransactionDetailResponse> transactionDetails =
                    getTransactionDetailsForAccount(depositAccountId);

            // Create enhanced response
            result.add(EnhancedUserRankingResponse.from(
                    basicRanking,
                    transactionDetails
            ));
        }

        return result;
    }

    private List<UserTransactionDetailResponse> getTransactionDetailsForAccount(Long accountId) {
        // Find the account
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return List.of(); // Return empty list if account not found
        }

        // Get all transaction details for this account
        Optional<List<EntertainerSavingsTransactionDetail>> transactionsOpt =
                transactionDetailRepository.findByDepositAccountId(accountId);

        if (transactionsOpt.isEmpty()) {
            return List.of(); // Return empty list if no transactions
        }

        // Convert to DTOs
        return transactionsOpt.get().stream()
                .map(transaction -> UserTransactionDetailResponse.from(
                        transaction.getId(),
                        transaction.getTransactionUniqueNo(),
                        LocalDateTime.now(), // Placeholder - replace with actual transaction time if available
                        transaction.getMessage(),
                        transaction.getTransactionBalance(),
                        transaction.getImageUrl()
                ))
                .collect(Collectors.toList());
    }
}