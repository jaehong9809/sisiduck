package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.dto.request.ApiCreateAccountResponse;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStarRequest;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerDepositResponse;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerResponse;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerSearchResponse;
import com.a702.finafanbe.core.entertainer.dto.response.InquireEntertainerAccountResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerRepository;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.response.StarAccountResponse;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.response.ResponseData;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.a702.finafanbe.global.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntertainSavingsService {

    private static final String EMAIL = "lsc7134@naver.com";

    private final EntertainerRepository entertainRepository;
    private final EntertainerSavingsAccountRepository entertainerSavingsAccountRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final EntertainerSavingsTransactionDetailRepository entertainerSavingsTransactionDetailRepository;

    @Transactional
    public StarAccountResponse createEntertainerSavings(
            CreateStarAccountRequest createStartAccountRequest,
            ApiCreateAccountResponse accountResponse,
            String withdrawalAccountNo
    ) {
        User user = findUser(EMAIL);
        Long entertainerId = findEntertainerId(createStartAccountRequest.entertainerId());

        validateNoExistingAccount(user.getUserId(), entertainerId);

        Account createdAccount = accountRepository.save(Account.of(
            user.getUserId(),
            accountResponse.accountNo(),
            accountResponse.currency()
        ));

        EntertainerSavingsAccount entertainerSavingsAccount = saveEntertainerSavingsAccount(
                user.getUserId(),
                entertainerId,
                createStartAccountRequest.productName(),
                createdAccount.getAccountId(),
                createStartAccountRequest.withdrawalAccountId()
        );
        return StarAccountResponse.of(
                entertainerSavingsAccount.getUserId(),
                entertainerSavingsAccount.getEntertainerId(),
                entertainerSavingsAccount.getDepositAccountId(),
                entertainerSavingsAccount.getWithdrawalAccountId()
        );
    }

    private EntertainerSavingsAccount saveEntertainerSavingsAccount(
            Long userId,
            Long entertainerId,
            String productName,
            Long depositAccountId,
            Long withdrawalAccountId
    ) {
        return entertainerSavingsAccountRepository.save(
                EntertainerSavingsAccount.of(
                    userId,
                    entertainerId,
                    productName,
                    depositAccountId,
                    withdrawalAccountId,
                    0.05,
                    5L,
                    "example.com"
                )
        );
    }

    private void validateNoExistingAccount(Long userId, Long entertainerId) {
        if(existEntertainerSavings(userId, entertainerId)){
            throw new BadRequestException(ResponseData.createResponse(EXIST_SAVINGS_PRODUCT));
        }
    }

    private boolean existEntertainerSavings(
            Long userId,
            Long entertainerId
    ) {
        return entertainerSavingsAccountRepository.existsByUserIdAndEntertainerId(
                userId,
                entertainerId
        );
    }

    private Long findEntertainerId(Long entertainerId) {
        return entertainRepository.findByEntertainerId(entertainerId)
                .orElseThrow(()-> new BadRequestException(ResponseData.createResponse(NotFoundEntertainer)))
                .getEntertainerId();
    }

    private User findUser(String userEmail) {
        log.info("Find user by email: " + userEmail);
        return userRepository.findBySocialEmail(userEmail)
                .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(NotFoundUser)));
    }

    @Transactional
    public EntertainerResponse choiceStar(
            SelectStarRequest selectStarRequest
    ) {
        User user = findUser(EMAIL);
        user.updateFavoriteEntertainer(findEntertainerId(selectStarRequest.entertainerId()));
        Entertainer entertainer = entertainRepository.findByEntertainerId(selectStarRequest.entertainerId()).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NotFoundEntertainer)));
        return EntertainerResponse.of(
                entertainer.getEntertainerName(),
                entertainer.getEntertainerProfileUrl(),
                entertainer.getFandomName()
        );
    }

    public List<Entertainer> findStars() {
        return entertainRepository.findAll();
    }


    public EntertainerDepositResponse deposit(
            String userEmail,
            Long depositAccountId,
            Long withdrawalAccountId,
            BigDecimal depositAmount,
            BigDecimal transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ) {
        Long userId = findUser(userEmail).getUserId();
        entertainerSavingsTransactionDetailRepository.save(
                EntertainerSavingsTransactionDetail.of(
                        userId,
                        depositAccountId,
                        withdrawalAccountId,
                        depositAmount,
                        transactionBalance,
                        transactionUniqueNo,
                        message,
                        imageUrl
                )
        );
        return EntertainerDepositResponse.of(
                depositAccountId,
                withdrawalAccountId,
                transactionBalance,
                transactionUniqueNo,
                message,
                imageUrl
        );
    }

    public EntertainerSavingsAccount findEntertainerAccountByDepositAccountId(Long savingAccountId) {
        return entertainerSavingsAccountRepository.findByDepositAccountId(savingAccountId).orElseThrow(
            () -> new BadRequestException(ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
    }

    public List<EntertainerSavingsAccount> findAccountByUserId(Long userId) {
        return entertainerSavingsAccountRepository.findByUserId(userId).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NotFoundUser)));
    }

    public List<EntertainerSearchResponse> searchEntertainers(String keyword) {
        List<Entertainer> entertainers;
        if (keyword == null || keyword.trim().isEmpty()) {
            entertainers = entertainRepository.findAll();
        }else {
            entertainers = entertainRepository.searchByNameOrFandom(keyword);
        }
        return entertainers.stream()
            .map(EntertainerSearchResponse::of)
            .collect(Collectors.toList());
    }
}
