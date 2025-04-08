package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.bank.application.BankService;
import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private final EntertainerSavingsTransactionDetailRepository entertainerSavingsTransactionDetailRepository;
    private final BankService bankService;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;

    @Transactional
    public StarAccountResponse createEntertainerSavings(
            CreateStarAccountRequest createStartAccountRequest,
            ApiCreateAccountResponse accountResponse
    ) {
        User user = findUser(EMAIL);
        Long entertainerId = findEntertainerId(createStartAccountRequest.entertainerId());

        validateNoExistingAccount(user.getUserId(), entertainerId);
        Bank bank = bankService.findBankByCode(accountResponse.bankCode());
        EntertainerSavingsAccount createdAccount = entertainerSavingsAccountRepository.save(EntertainerSavingsAccount.of(
            user.getUserId(),
            entertainerId,
            bank.getBankId(),
            createStartAccountRequest.productName(),
            accountResponse.accountNo(),
            createStartAccountRequest.withdrawalAccountId(),
            0.05,
            60L,
            "example.com"

        ));

        return StarAccountResponse.of(
            createdAccount.getUserId(),
            createdAccount.getEntertainerId(),
            createdAccount.getId(),
            createdAccount.getWithdrawalAccountId(),
            bank
        );
    }

    @Transactional
    public EntertainerResponse choiceStar(
            SelectStarRequest selectStarRequest
    ) {
        User user = findUser(EMAIL);
        user.updateFavoriteEntertainer(findEntertainerId(selectStarRequest.entertainerId()));
        Entertainer entertainer = entertainRepository.findByEntertainerId(selectStarRequest.entertainerId()).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NotFoundEntertainer)));
        return EntertainerResponse.of(
                entertainer.getEntertainerId(),
                entertainer.getEntertainerName(),
                entertainer.getEntertainerProfileUrl(),
                entertainer.getFandomName()
        );
    }

    @Transactional(readOnly = true)
    public List<Entertainer> findStars() {
        return entertainRepository.findAll();
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public EntertainerSavingsAccount findEntertainerAccountById(Long savingAccountId) {
        return entertainerSavingsAccountRepository.findById(savingAccountId).orElseThrow(
            () -> new BadRequestException(ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
    }

    @Transactional(readOnly = true)
    public boolean existsEntertainerAccountByWithdrawalAccountId(Long savingAccountId) {
        return entertainerSavingsAccountRepository.existsByWithdrawalAccountId(savingAccountId);
    }

    @Transactional(readOnly = true)
    public List<EntertainerSavingsAccount> findAccountByUserId(Long userId) {
        return entertainerSavingsAccountRepository.findByUserId(userId).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NotFoundUser)));
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public InquireEntertainerAccountResponse putAccountAlias(
            Long savingAccountId,
            String newName
    ) {
        EntertainerSavingsAccount savingsAccount = findEntertainerAccountById(savingAccountId);
        long maintenanceDays = savingsAccount.getMaintenanceDays(savingsAccount);
        Bank bank = bankService.findBankById(savingsAccount.getBankId());
        Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(savingsAccount.getWithdrawalAccountId());
        Bank withdrawalBank = bankService.findBankById(withdrawalAccount.getBankId());
        savingsAccount.updateAccountName(newName);
        return InquireEntertainerAccountResponse.of(
                savingsAccount.getId(),
                savingsAccount.getAccountNo(),
                savingsAccount.getProductName(),
                savingsAccount.getAmount(),
                savingsAccount.getCreatedAt(),
                savingsAccount.getInterestRate(),
                savingsAccount.getDuration(),
                maintenanceDays,
                savingsAccount.getImageUrl(),
                withdrawalAccount,
                bank,
                withdrawalBank
        );
    }

    @Transactional
    public void deleteByAccountId(Long accountId) {
        entertainerSavingsAccountRepository.deleteById(accountId);
    }

    public boolean existsEntertainerAccountById(Long accountId) {
        return entertainerSavingsAccountRepository.existsById(accountId);
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
        return entertainerSavingsAccountRepository.existsByUserIdAndEntertainerIdAndDeletedAtNull(
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
        return userRepository.findBySocialEmail(userEmail)
                .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(NotFoundUser)));
    }

    public List<String> findAllAccountNos() {
        return entertainerSavingsAccountRepository.findAllAccountNos();
    }

    public EntertainerSavingsAccount findEntertainerAccountByAccountNo(String depositAccountNo) {
        return entertainerSavingsAccountRepository.findByAccountNo(depositAccountNo).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
    }

    @Transactional
    public void updateAccount(EntertainerSavingsAccount savingsAccount, Long amount) {
        savingsAccount.addAmount(amount);
    }
}
