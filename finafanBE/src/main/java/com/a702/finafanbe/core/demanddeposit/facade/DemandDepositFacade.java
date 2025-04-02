package com.a702.finafanbe.core.demanddeposit.facade;

import com.a702.finafanbe.core.bank.application.BankService;
import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.application.DeleteAccountService;
import com.a702.finafanbe.core.demanddeposit.application.ExternalDemandDepositApiService;
import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.*;
import com.a702.finafanbe.core.demanddeposit.dto.response.*;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse.REC;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.application.EntertainerService;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.response.*;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.ranking.application.RankingService;
import com.a702.finafanbe.core.transaction.deposittransaction.application.DepositTransactionService;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.user.application.UserService;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemandDepositFacade {

    private static final String EMAIL = "lsc7134@naver.com";

    private final ExternalDemandDepositApiService externalDemandDepositApiService;
    private final EntertainSavingsService entertainSavingsService;
    private final DepositTransactionService depositTransactionService;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;
    private final UserService userService;
    private final BankService bankService;
    private final EntertainerService entertainerService;
    private final RankingService rankingService;
    private final DeleteAccountService deleteAccountService;
    private final FinancialRequestFactory financialRequestFactory;

    public ResponseEntity<InquireDemandDepositAccountResponse> getDemandDepositAccount(
            String userEmail,
            String accountNo
    ) {
        return externalDemandDepositApiService.DemandDepositRequest(
                "/demandDeposit/inquireDemandDepositAccount",
                userEmail,
                accountNo,"inquireDemandDepositAccount"
        );
    }

    public ResponseEntity<InquireDemandDepositAccountListResponse> getDemandDepositListAccount(String userEmail) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/inquireDemandDepositAccountList",
                apiName -> financialRequestFactory.userKeyRequest(userEmail, apiName),
                "inquireDemandDepositAccountList",
                InquireDemandDepositAccountListResponse.class
        );
    }

    public ResponseEntity<DeleteAccountResponse> deleteAccount(
            String accountNo,
            String refundAccountNo
    ) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/deleteDemandDepositAccount",
                apiName -> financialRequestFactory.deleteAccountRequest(
                        EMAIL,
                        accountNo,
                        apiName,
                        refundAccountNo
                ),
                "deleteDemandDepositAccount",
                DeleteAccountResponse.class
        );
    }

    public ResponseEntity<AccountTransactionHistoriesResponse> inquireHistories(
            TransactionHistoriesRequest transactionHistoriesRequest
    ) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/inquireTransactionHistoryList",
                apiName -> financialRequestFactory.inquireHistories(
                        transactionHistoriesRequest.email(),
                        apiName,
                        transactionHistoriesRequest.accountNo(),
                        transactionHistoriesRequest.startDate(),
                        transactionHistoriesRequest.endDate(),
                        transactionHistoriesRequest.transactionType(),
                        transactionHistoriesRequest.orderByType()
                ),
                "inquireTransactionHistoryList",
                AccountTransactionHistoriesResponse.class
        );
    }
//TODO
    public ResponseEntity<InquireAccountHolderNameResponse> inquireAccountHolderName(
            String userEmail,
            String accountNo
    ) {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireDemandDepositAccountHolderName",
                financialRequestFactory.UserKeyAccountNoRequest(
                        userEmail,
                        accountNo,
                        "inquireDemandDepositAccountHolderName"
                ),
                InquireAccountHolderNameResponse.class
        );
    }

    public ResponseEntity<InquireAccountBalanceResponse> inquireAccountBalance(
            String userEmail,
            String accountNo
    ) {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireDemandDepositAccountBalance",
                financialRequestFactory.UserKeyAccountNoRequest(
                        userEmail,
                        accountNo,
                        "inquireDemandDepositAccountBalance"
                ),
                InquireAccountBalanceResponse.class
        );
    }

    public ApiCreateAccountResponse createEntertainerAccount(
            String email
    ) {
        User user = userService.findUserByEmail(email);
        RetrieveProductsResponse.REC rec = getProducts()
                .REC()
                .stream()
                .filter(product -> product.getAccountName().equals("연예인 적금"))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NOT_FOUND_DEMAND_DEPOSIT_PRODUCT)));
        String productUniqueNo = rec.getAccountTypeUniqueNo();
        REC createDemandDepositAccount = apiClientUtil.callFinancialNetwork(
                "/demandDeposit/createDemandDepositAccount",
                financialRequestFactory.UserKeyAccountTypeUniqueNoRequest(
                        email,
                        rec.getAccountTypeUniqueNo(),
                        "createDemandDepositAccount"
                ),
                CreateAccountResponse.class
        ).getBody().REC();
        return ApiCreateAccountResponse.of(
            user.getUserId(),
            createDemandDepositAccount.getAccountNo(),
            createDemandDepositAccount.getBankCode(),
            createDemandDepositAccount.getCurrency().getCurrency(),
            productUniqueNo
        );
    }

    public ResponseEntity<InquireDemandDepositAccountTransactionHistoryResponse> inquireHistory(TransactionHistoryRequest transactionHistoryRequest) {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireTransactionHistory",
                financialRequestFactory.inquireHistory(
                        transactionHistoryRequest.email(),
                        "inquireTransactionHistory",
                        transactionHistoryRequest.accountNo(),
                        transactionHistoryRequest.transactionUniqueNo()
                ),
                InquireDemandDepositAccountTransactionHistoryResponse.class
        );
    }

    public ResponseEntity<UpdateDemandDepositAccountDepositResponse> depositAccount(
            String userEmail,
            DepositRequest depositRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/updateDemandDepositAccountDeposit",
                financialRequestFactory.depositAccount(
                        userEmail,
                        "updateDemandDepositAccountDeposit",
                        depositRequest.accountNo(),
                        depositRequest.transactionBalance(),
                        depositRequest.transactionSummary()
                ),
                UpdateDemandDepositAccountDepositResponse.class
        );
    }

    public RetrieveProductsResponse getProducts() {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireDemandDepositList",
                financialRequestFactory.inquireProducts(
                        "inquireDemandDepositList"
                ),
                RetrieveProductsResponse.class
        ).getBody();
    }

    public StarAccountResponse createEntertainerSavings(CreateStarAccountRequest createStarAccountRequest){

        ApiCreateAccountResponse response = createEntertainerAccount(
            EMAIL
        );
        return entertainSavingsService.createEntertainerSavings(
                createStarAccountRequest,
                response
        );
    }

    public InquireEntertainerAccountResponse getEntertainerAccount(
            String userEmail,
            Long savingAccountId
    ) {
        EntertainerSavingsAccount savingsAccount = entertainSavingsService.findEntertainerAccountByDepositAccountId(savingAccountId);
        Account depositAccount = inquireDemandDepositAccountService.findAccountById(
            savingsAccount.getDepositAccountId());
        Bank bank = bankService.findBankById(depositAccount.getBankId());
        Account withDrawalAccount = inquireDemandDepositAccountService.findAccountById(savingsAccount.getWithdrawalAccountId());
        Bank withdrawalBank = bankService.findBankById(withDrawalAccount.getBankId());
        return InquireEntertainerAccountResponse.of(
            depositAccount.getAccountId(),
            depositAccount.getAccountNo(),
            depositAccount.getAccountName(),
            depositAccount.getAmount(),
            depositAccount.getCreatedAt(),
            depositAccount.getInterestRate(),
            savingsAccount.getDuration(),
            savingsAccount.getImageUrl(),
            withDrawalAccount,
            bank,
            withdrawalBank
        );
    }

    public EntertainerAccountsResponse findStarAccounts(String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        List<EntertainerSavingsAccount> starAccounts = entertainSavingsService.
            findAccountByUserId(user.getUserId());
        BigDecimal totalValue = starAccounts.stream().map(
                account-> {
                    Account depositAccount = inquireDemandDepositAccountService.findAccountById(account.getDepositAccountId());
                    return depositAccount.getAmount();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return EntertainerAccountsResponse.of(
                totalValue,
                starAccounts.stream()
                        .map(savingsAccount -> {
                            Account depositAccount = inquireDemandDepositAccountService.findAccountById(
                                    savingsAccount.getDepositAccountId());
                            Bank bank = bankService.findBankById(depositAccount.getBankId());
                            Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(
                                    savingsAccount.getWithdrawalAccountId());
                            Bank withdrawalBank = bankService.findBankById(withdrawalAccount.getBankId());
                            return InquireEntertainerAccountResponse.of(
                                    depositAccount.getAccountId(),
                                    depositAccount.getAccountNo(),
                                    depositAccount.getAccountName(),
                                    depositAccount.getAmount(),
                                    depositAccount.getCreatedAt(),
                                    depositAccount.getInterestRate(),
                                    savingsAccount.getDuration(),
                                    savingsAccount.getImageUrl(),
                                    withdrawalAccount,
                                    bank,
                                    withdrawalBank
                            );
                        })
                        .collect(Collectors.toList())
        );
    }

    public InquireEntertainerHistoriesResponse inquireEntertainerHistories(
            Long savingAccountId
    ) {
        Account depositAccount = inquireDemandDepositAccountService.findAccountById(savingAccountId);
        AccountTransactionHistoriesResponse.REC inquireTransactionHistoryList = apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireTransactionHistoryList",
                financialRequestFactory.inquireHistories(
                    EMAIL,
                    "inquireTransactionHistoryList",
                    depositAccount.getAccountNo(),
                    "00000101",
                    DateUtil.getTransmissionDate(),
                    "A",
                    "DESC"
                ),
                AccountTransactionHistoriesResponse.class
        ).getBody().REC();
        Account account = inquireDemandDepositAccountService.findAccountById(savingAccountId);
        List<EntertainerSavingsTransactionDetail> transactionDetails = depositTransactionService.getEntertainerAccountTransactionsByAccountId(
            account.getAccountId());
        Map<Long, EntertainerSavingsTransactionDetail> transactionImageMap = transactionDetails.stream().collect(
                Collectors.toMap(
                        EntertainerSavingsTransactionDetail::getTransactionUniqueNo,
                        detail -> detail,
                        (existingValue, newValue) -> existingValue
                )
        );

        List<TransactionWithImageResponse> transactionsWithImages = inquireTransactionHistoryList.list().stream()
            .filter(transaction ->{
                EntertainerSavingsTransactionDetail detail = transactionImageMap.get(transaction.transactionUniqueNo());
                return detail != null;
            })
            .map(transaction -> {
                EntertainerSavingsTransactionDetail detail = transactionImageMap.get(transaction.transactionUniqueNo());
                String imageUrl = detail != null ? detail.getImageUrl() : "";

                return new TransactionWithImageResponse(
                    detail.getId(),
                    transaction.transactionUniqueNo(),
                    transaction.transactionAfterBalance(),
                    transaction.transactionBalance(),
                    transaction.transactionMemo(),
                    imageUrl
                );
            })
            .collect(Collectors.toList());

        return InquireEntertainerHistoriesResponse.of(
                inquireTransactionHistoryList.totalCount(),
                transactionsWithImages
        );
    }

    public ResponseEntity<UpdateDemandDepositAccountTransferResponse> transferAccount(
            String userEmail,
            TransferRequest transferRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/updateDemandDepositAccountTransfer",
                financialRequestFactory.transferAccount(
                        userEmail,
                        "updateDemandDepositAccountTransfer",
                        transferRequest.depositAccountNo(),
                        transferRequest.depositTransactionSummary(),
                        transferRequest.transactionBalance(),
                        transferRequest.withdrawalAccountNo(),
                        transferRequest.withdrawalTransactionSummary()
                ),
                UpdateDemandDepositAccountTransferResponse.class
        );
    }

    public ResponseEntity<UpdateDemandDepositAccountTransferResponse> transferEntertainerAccount(
        Long depositAccountId,
        Long transactionBalance
    ) {
            EntertainerSavingsAccount entertainerSavingsAccount = entertainSavingsService.findEntertainerAccountByDepositAccountId(depositAccountId);
            Account depositAccount = inquireDemandDepositAccountService.findAccountById(entertainerSavingsAccount.getDepositAccountId());
            Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(entertainerSavingsAccount.getWithdrawalAccountId());
            TransferRequest transferRequest = new TransferRequest(
                    depositAccount.getAccountNo(),
                    "",
                    transactionBalance,
                    withdrawalAccount.getAccountNo(),
                    ""
            );
            return apiClientUtil.callFinancialNetwork(
                    "/demandDeposit/updateDemandDepositAccountTransfer",
                    financialRequestFactory.transferAccount(
                            EMAIL,
                            "updateDemandDepositAccountTransfer",
                            transferRequest.depositAccountNo(),
                            transferRequest.depositTransactionSummary(),
                            transferRequest.transactionBalance(),
                            transferRequest.withdrawalAccountNo(),
                            transferRequest.withdrawalTransactionSummary()
                    ),
                    UpdateDemandDepositAccountTransferResponse.class
            );
        }

    public List<HomeEntertainerAccountResponse> findStarAccountsForHome(String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        List<EntertainerSavingsAccount> starAccounts = entertainSavingsService.
                findAccountByUserId(user.getUserId());
        return starAccounts.stream()
                .map(savingsAccount -> {
                    Account depositAccount = inquireDemandDepositAccountService.findAccountById(
                            savingsAccount.getDepositAccountId());
                    Bank bank = bankService.findBankById(depositAccount.getBankId());
                    Entertainer entertainer = entertainerService.findEntertainerById(savingsAccount.getEntertainerId());
                    Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(
                            savingsAccount.getWithdrawalAccountId());
                    Bank withdrawalBank = bankService.findBankById(withdrawalAccount.getBankId());
                    return HomeEntertainerAccountResponse.of(
                            depositAccount.getAccountId(),
                            entertainer.getEntertainerName(),
                            entertainer.getEntertainerProfileUrl(),
                            depositAccount.getAccountNo(),
                            depositAccount.getAmount()
                    );
                })
                .collect(Collectors.toList());
    }

    public List<EntertainerResponse> getPossessionEntertainer() {
        String userEmail = EMAIL; // 인증 사용자로 대체 필요
        User user = userService.findUserByEmail(userEmail);

        List<EntertainerSavingsAccount> accounts =
                entertainSavingsService.findAccountByUserId(user.getUserId());

        Set<Long> entertainerIds = accounts.stream()
                .map(EntertainerSavingsAccount::getEntertainerId)
                .collect(Collectors.toSet());

        return entertainerIds.stream()
                .map(id -> {
                    Entertainer entertainer = entertainerService.findEntertainerById(id);

                    return new EntertainerResponse(
                            entertainer.getEntertainerName(),
                            entertainer.getEntertainerProfileUrl(),
                            entertainer.getFandomName()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteStarAccount(Long savingAccountId) {
        Account account = inquireDemandDepositAccountService.findAccountById(savingAccountId);
        EntertainerSavingsAccount savingsAccount = entertainSavingsService.findEntertainerAccountByDepositAccountId(savingAccountId);
        Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(savingsAccount.getWithdrawalAccountId());
        log.info("account {}" , account.getAccountNo());
        log.info("Waccount {}" , withdrawalAccount.getAccountNo());
        DeleteAccountResponse.REC deleteResponse = deleteAccountService.deleteAccount(
                "/demandDeposit/deleteDemandDepositAccount",
                financialRequestFactory.deleteAccountRequest(
                        EMAIL,
                        account.getAccountNo(),
                        "deleteDemandDepositAccount",
                        withdrawalAccount.getAccountNo()
                )
        ).getBody().REC();

        Account depositAccount = inquireDemandDepositAccountService.findAccountByAccountNo(deleteResponse.accountNo());
        if (account.getAmount() != null && account.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            if (savingsAccount.isPresent()) {
                double amountToDeduct = account.getAmount().negate().doubleValue();
                rankingService.updateRanking(
                        savingsAccount.getUserId(),
                        savingsAccount.getEntertainerId(),
                        amountToDeduct
                );
            }
        }

        entertainSavingsService.deleteByAccountId(depositAccount.getAccountId());
        deleteAccountService.deleteById(account.getAccountId());
    }

    public void deleteStarWithdrawalAccount(Long accountId) {
        if(entertainSavingsService.existsEntertainerAccountByWithdrawalAccountId(accountId)){
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.ENTERTAINER_SAVINGS_CONSTRAINT));
        }
        if(entertainSavingsService.existsEntertainerAccountByDepositAccountId(accountId)){
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.ENTERTAINER_SAVINGS));
        }
        deleteAccountService.deleteById(accountId);
    }
}
