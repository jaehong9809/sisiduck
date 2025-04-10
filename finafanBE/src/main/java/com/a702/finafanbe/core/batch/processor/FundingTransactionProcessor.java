package com.a702.finafanbe.core.batch.processor;

import com.a702.finafanbe.core.batch.dto.TransactionRequest;
import com.a702.finafanbe.core.batch.dto.TransactionResponse;
import com.a702.finafanbe.core.batch.exception.RetryTransactionException;
import com.a702.finafanbe.core.batch.exception.SkipTransactionException;
import com.a702.finafanbe.core.demanddeposit.application.ExternalDemandDepositApiService;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferResponse;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.FundingTransactionStatus;
import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupUserRepository;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class FundingTransactionProcessor implements ItemProcessor<FundingPendingTransaction, TransactionResponse> {

    private final GroupUserRepository groupUserRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final FinancialRequestFactory financialRequestFactory;
    private final ExternalDemandDepositApiService externalDemandDepositApiService;
    private final FundingGroupRepository fundingGroupRepository;

    private final String SUCCESS_CODE = "H0000";
    private final String NO_MONEY = "A1014";
    private final String CANT_SEND_MONEY = "A1017";

    @Override
    public TransactionResponse process(FundingPendingTransaction tx) throws Exception {
        try {
            TransactionRequest request = transactionDtoFactory(tx);
            return apiTransaction(request);
        } catch (SkipTransactionException e) {
            log.warn("[Batch Skip] txId: {}, reason: {}", tx.getId(), e.getMessage());
            throw e;
        }
    }

    private TransactionRequest transactionDtoFactory(FundingPendingTransaction tx) {
        FundingGroup funding = fundingGroupRepository.findById(tx.getFundingId())
                .orElseThrow(() -> new SkipTransactionException("펀딩을 찾을 수 없습니다."));
        GroupUser admin = groupUserRepository.findByFundingGroupIdAndRole(funding.getId(), Role.ADMIN)
                .orElseThrow(() -> new SkipTransactionException("주최 회원을 찾을 수 없습니다."));
        User user = userRepository.findById(tx.getUserId()).orElseThrow(() -> new SkipTransactionException("펀딩 참여자가 없습니다."));
        User adminUser = userRepository.findById(admin.getId())
                .orElseThrow(() -> new SkipTransactionException("해당 유저가 존재하지 않습니다."));
        Account depositAccount = accountRepository.findById(tx.getAccountId())
                .orElseThrow(() -> new SkipTransactionException("출금할 계좌가 존재하지 않습니다."));
        Account adminAccount = accountRepository.findByAccountId(adminUser.getRepresentAccountId())
                .orElseThrow(() -> new SkipTransactionException("대표 계좌가 설정되어 있지 않습니다."));

        return TransactionRequest.of(tx.getId(), user.getSocialEmail(), adminAccount.getAccountNo(), tx.getBalance(), depositAccount.getAccountNo(), funding.getName(), funding.getName());
    }

    private TransactionResponse apiTransaction(TransactionRequest request) {
        try {
            UpdateDemandDepositAccountTransferResponse response = externalDemandDepositApiService.DemandDepositRequestWithFactory(
                    "/demandDeposit/updateDemandDepositAccountTransfer",
                    apiName -> financialRequestFactory.transferAccount(
                            request.userEmail(),
                            apiName,
                            request.depositAccountNo(),
                            request.depositTransactionSummary(),
                            request.transactionBalance(),
                            request.withdrawalAccountNo(),
                            request.withdrawalTransactionSummary()
                    ),
                    "updateDemandDepositAccountTransfer",
                    UpdateDemandDepositAccountTransferResponse.class
            ).getBody();

            String responseCode = response.Header().getResponseCode();
            log.info("[Batch Response] txId: {}, user: {}, responseCode: {}", request.id(), request.userEmail(), responseCode);

            if (responseCode.equals(NO_MONEY) || responseCode.equals(CANT_SEND_MONEY)) {
                log.warn("[Batch Result] txId: {}, user: {}, status: SKIPPED (잔액 부족 or 한도 초과)", request.id(), request.userEmail());
                return TransactionResponse.of(request.id(), FundingTransactionStatus.SKIPPED);
            } else if (responseCode.equals(SUCCESS_CODE)) {
                log.info("[Batch Result] txId: {}, user: {}, status: SUCCESS", request.id(), request.userEmail());
                Account withDrawlAccount = accountRepository.findByAccountNo(request.withdrawalAccountNo()).get();
                withDrawlAccount.addAmount(BigDecimal.valueOf(request.transactionBalance()).negate());
                Account depositAccount = accountRepository.findByAccountNo(request.depositAccountNo()).get();
                depositAccount.addAmount(BigDecimal.valueOf(request.transactionBalance()));
                return TransactionResponse.of(request.id(), FundingTransactionStatus.SUCCESS);
            } else {
                throw new RetryTransactionException("API 통신 문제 : " + responseCode);
            }

        } catch (RetryTransactionException e) {
            throw e;
        } catch (Exception e) {
            log.error("[Batch Fail] txId: {}, user: {}, reason: {}", request.id(), request.userEmail(), e.getMessage(), e);
            throw new RetryTransactionException("API 통신 문제");
        }
    }
}
