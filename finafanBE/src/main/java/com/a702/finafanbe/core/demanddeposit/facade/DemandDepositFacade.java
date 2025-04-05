package com.a702.finafanbe.core.demanddeposit.facade;

import com.a702.finafanbe.core.bank.application.BankService;
import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.application.*;
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
import com.a702.finafanbe.global.common.financialnetwork.util.ApiConstants;
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

import static com.a702.finafanbe.global.common.financialnetwork.util.ApiConstants.INQUIRE_DEMAND_DEPOSIT_ACCOUNT_PATH;

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

    public InquireDemandDepositAccountResponse getDemandDepositAccount(
            String userEmail,
            String accountNo
    ) {
        return externalDemandDepositApiService.DemandDepositRequest(
                INQUIRE_DEMAND_DEPOSIT_ACCOUNT_PATH,
                userEmail,
                accountNo,
                ApiConstants.extractApiName(INQUIRE_DEMAND_DEPOSIT_ACCOUNT_PATH)
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

    public AccountTransactionHistoriesResponse.REC inquireHistories(
            TransactionHistoriesRequest transactionHistoriesRequest
    ) {
        Account depositAccount = inquireDemandDepositAccountService.findAccountById(transactionHistoriesRequest.accountId());
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/inquireTransactionHistoryList",
                apiName -> financialRequestFactory.inquireHistories(
                        EMAIL,
                        apiName,
                        depositAccount.getAccountNo(),
                        "00000101",
                        DateUtil.getTransmissionDate(),
                        "A",
                        "DESC"
                ),
                "inquireTransactionHistoryList",
                AccountTransactionHistoriesResponse.class
        ).getBody().REC();
    }

    public ResponseEntity<InquireAccountHolderNameResponse> inquireAccountHolderName(
            String userEmail,
            String accountNo
    ) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/inquireDemandDepositAccountHolderName",
                apiName ->financialRequestFactory.UserKeyAccountNoRequest(
                        userEmail,
                        accountNo,
                        apiName
                ),
                "inquireDemandDepositAccountHolderName",
                InquireAccountHolderNameResponse.class
        );
    }

    public ResponseEntity<InquireAccountBalanceResponse> inquireAccountBalance(
            String userEmail,
            String accountNo
    ) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/inquireDemandDepositAccountBalance",
                apiName->financialRequestFactory.UserKeyAccountNoRequest(
                        userEmail,
                        accountNo,
                        apiName
                ),
                "inquireDemandDepositAccountBalance",
                InquireAccountBalanceResponse.class
        );
    }

    public ApiCreateAccountResponse createEntertainerAccount(
            String email
    ) {
        User user = userService.findUserByEmail(email);
        String productUniqueNo = getEntertainerProductUniqueNo();
        REC createDemandDepositAccount = createEntertainerAccount(email, productUniqueNo);
        return ApiCreateAccountResponse.of(
            user.getUserId(),
            createDemandDepositAccount.getAccountNo(),
            createDemandDepositAccount.getBankCode(),
            createDemandDepositAccount.getCurrency().getCurrency(),
            productUniqueNo
        );
    }

    public ResponseEntity<InquireDemandDepositAccountTransactionHistoryResponse> inquireHistory(TransactionHistoryRequest transactionHistoryRequest) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/inquireTransactionHistory",
                apiName->financialRequestFactory.inquireHistory(
                        transactionHistoryRequest.email(),
                        apiName,
                        transactionHistoryRequest.accountNo(),
                        transactionHistoryRequest.transactionUniqueNo()
                ),
                "inquireTransactionHistory",
                InquireDemandDepositAccountTransactionHistoryResponse.class
        );
    }

    public ResponseEntity<UpdateDemandDepositAccountDepositResponse> depositAccount(
            String userEmail,
            DepositRequest depositRequest
    ) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/updateDemandDepositAccountDeposit",
                apiName -> financialRequestFactory.depositAccount(
                        userEmail,
                        apiName,
                        depositRequest.accountNo(),
                        depositRequest.transactionBalance(),
                        depositRequest.transactionSummary()
                ),
                "updateDemandDepositAccountDeposit",
                UpdateDemandDepositAccountDepositResponse.class
        );
    }

    public RetrieveProductsResponse getProducts() {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/inquireDemandDepositList",
                apiName->financialRequestFactory.inquireProducts(
                        apiName
                ),
                "inquireDemandDepositList",
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

        AccountTransactionHistoriesResponse.REC inquireTransactionHistoryList = entertainerAccountHistories(depositAccount);

        List<EntertainerSavingsTransactionDetail> transactionDetails = depositTransactionService.getEntertainerAccountTransactionsByAccountId(
            depositAccount.getAccountId());
        Map<Long, EntertainerSavingsTransactionDetail> transactionImageMap = transactionDetails.stream().collect(
                Collectors.toMap(
                        EntertainerSavingsTransactionDetail::getTransactionUniqueNo,
                        detail -> detail,
                        (existingValue, newValue) -> existingValue
                )
        );
        log.info("Transaction history total count: {}", inquireTransactionHistoryList.totalCount());
        log.info("Transaction history list size: {}", inquireTransactionHistoryList.list().size());
        log.info("Transaction image map size: {}", transactionImageMap.size());


        List<TransactionWithImageResponse> transactionsWithImages = inquireTransactionHistoryList.list().stream()
                .peek(transaction -> {
                    if (transactionImageMap.get(transaction.transactionUniqueNo()) == null) {
                        log.info("Transaction {} was filtered out - no matching detail record",
                                transaction.transactionUniqueNo());
                    }
                })
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

    private AccountTransactionHistoriesResponse.REC entertainerAccountHistories(Account depositAccount) {
        return inquireHistories(TransactionHistoriesRequest.of(depositAccount.getAccountId()));
    }

    public ResponseEntity<UpdateDemandDepositAccountTransferResponse> transferAccount(
            String userEmail,
            TransferRequest transferRequest
    ) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/updateDemandDepositAccountTransfer",
                apiName->financialRequestFactory.transferAccount(
                        userEmail,
                        apiName,
                        transferRequest.depositAccountNo(),
                        transferRequest.depositTransactionSummary(),
                        transferRequest.transactionBalance(),
                        transferRequest.withdrawalAccountNo(),
                        transferRequest.withdrawalTransactionSummary()
                ),
                "updateDemandDepositAccountTransfer",
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
            return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                    "/demandDeposit/updateDemandDepositAccountTransfer",
                    apiName -> financialRequestFactory.transferAccount(
                            EMAIL,
                            apiName,
                            transferRequest.depositAccountNo(),
                            transferRequest.depositTransactionSummary(),
                            transferRequest.transactionBalance(),
                            transferRequest.withdrawalAccountNo(),
                            transferRequest.withdrawalTransactionSummary()
                    ),
                    "updateDemandDepositAccountTransfer",
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
                    Entertainer entertainer = entertainerService.findEntertainerById(savingsAccount.getEntertainerId());
                    Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(
                            savingsAccount.getWithdrawalAccountId());
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
        String userEmail = EMAIL;
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
                            entertainer.getEntertainerId(),
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
        ).REC();

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

    private REC createEntertainerAccount(String email, String productUniqueNo) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
                "/demandDeposit/createDemandDepositAccount",
                apiName -> financialRequestFactory.UserKeyAccountTypeUniqueNoRequest(
                        email,
                        productUniqueNo,
                        apiName
                ),
                "createDemandDepositAccount",
                CreateAccountResponse.class
        ).getBody().REC();
    }

    private String getEntertainerProductUniqueNo() {
        return getProducts()
                .REC()
                .stream()
                .filter(product -> product.getAccountName().equals("연예인 적금"))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NOT_FOUND_DEMAND_DEPOSIT_PRODUCT)))
                .getAccountTypeUniqueNo();
    }
}
