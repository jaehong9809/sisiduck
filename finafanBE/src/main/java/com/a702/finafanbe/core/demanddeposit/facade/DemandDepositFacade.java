package com.a702.finafanbe.core.demanddeposit.facade;

import com.a702.finafanbe.core.bank.application.BankService;
import com.a702.finafanbe.core.bank.dto.response.BankAccountConnectionResponse;
import com.a702.finafanbe.core.bank.dto.response.BankAccountResponse;
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
import com.a702.finafanbe.core.user.dto.response.UserFinancialNetworkResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.util.ApiConstants;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.DateUtil;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final CreateAccountService createAccountService;

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
        EntertainerSavingsAccount depositAccount = entertainSavingsService.findEntertainerAccountById(transactionHistoriesRequest.accountId());
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

    public ApiCreateAccountResponse createAccount(
            String email
    ) {
        User user = userService.findUserByEmail(email);
        String productUniqueNo = getEntertainerProductUniqueNo();
        log.info("beforeExternalRequest");
        REC createDemandDepositAccount = createAccount(email, productUniqueNo);
        log.info("afterExternalRequest : " + createDemandDepositAccount);
        return ApiCreateAccountResponse.of(
            user.getUserId(),
            user.getSocialEmail(),
            createDemandDepositAccount.getAccountNo(),
            createDemandDepositAccount.getBankCode(),
            createDemandDepositAccount.getCurrency().getCurrency(),
            productUniqueNo
        );
    }

    public ApiCreateAccountResponse createTestAccount(
        String email
    ) {
        User user = userService.findUserByEmail(email);
        String productUniqueNo = getDummyProductUniqueNo();
        REC createDemandDepositAccount = createAccount(email, productUniqueNo);
        return ApiCreateAccountResponse.of(
            user.getUserId(),
            user.getSocialEmail(),
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
        ApiCreateAccountResponse response = createAccount(
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
        EntertainerSavingsAccount savingsAccount = entertainSavingsService.findEntertainerAccountById(savingAccountId);
        long maintenanceDays = savingsAccount.getMaintenanceDays(savingsAccount);
        Bank bank = bankService.findBankById(savingsAccount.getBankId());
        Account withDrawalAccount = inquireDemandDepositAccountService.findAccountById(savingsAccount.getWithdrawalAccountId());
        Bank withdrawalBank = bankService.findBankById(withDrawalAccount.getBankId());
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
            withDrawalAccount,
            bank,
            withdrawalBank
        );
    }

    public EntertainerAccountsResponse findStarAccounts(String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        List<EntertainerSavingsAccount> starAccounts = entertainSavingsService.
            findAccountByUserId(user.getUserId());
        BigDecimal totalValue = starAccounts.stream()
            .map(EntertainerSavingsAccount::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return EntertainerAccountsResponse.of(
                totalValue,
                starAccounts.stream()
                        .map(savingsAccount -> {
                            EntertainerSavingsAccount depositAccount = entertainSavingsService.findEntertainerAccountById(
                                    savingsAccount.getId());
                            long maintenanceDays = savingsAccount.getMaintenanceDays(depositAccount);
                            Bank bank = bankService.findBankById(depositAccount.getBankId());
                            Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(
                                    savingsAccount.getWithdrawalAccountId());
                            Bank withdrawalBank = bankService.findBankById(withdrawalAccount.getBankId());
                            return InquireEntertainerAccountResponse.of(
                                    depositAccount.getId(),
                                    depositAccount.getAccountNo(),
                                    depositAccount.getProductName(),
                                    depositAccount.getAmount(),
                                    depositAccount.getCreatedAt(),
                                    savingsAccount.getInterestRate(),
                                    savingsAccount.getDuration(),
                                    maintenanceDays,
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

        EntertainerSavingsAccount depositAccount = entertainSavingsService.findEntertainerAccountById(savingAccountId);

        AccountTransactionHistoriesResponse.REC inquireTransactionHistoryList = entertainerAccountHistories(depositAccount);

        List<EntertainerSavingsTransactionDetail> transactionDetails = depositTransactionService.getEntertainerAccountTransactionsByAccountId(
            depositAccount.getId());
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

    private AccountTransactionHistoriesResponse.REC entertainerAccountHistories(EntertainerSavingsAccount depositAccount) {
        return inquireHistories(TransactionHistoriesRequest.of(depositAccount.getId()));
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
            EntertainerSavingsAccount entertainerSavingsAccount = entertainSavingsService.findEntertainerAccountById(depositAccountId);
            Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(entertainerSavingsAccount.getWithdrawalAccountId());
            TransferRequest transferRequest = new TransferRequest(
                entertainerSavingsAccount.getAccountNo(),
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
                    Entertainer entertainer = entertainerService.findEntertainerById(savingsAccount.getEntertainerId());
                    return HomeEntertainerAccountResponse.of(
                        savingsAccount.getId(),
                        entertainer.getEntertainerName(),
                        entertainer.getEntertainerProfileUrl(),
                        savingsAccount.getAccountNo(),
                        savingsAccount.getAmount()
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

    public void deleteStarAccount(Long savingAccountId) {
        EntertainerSavingsAccount savingsAccount = entertainSavingsService.findEntertainerAccountById(savingAccountId);
        Account withdrawalAccount = inquireDemandDepositAccountService.findAccountById(savingsAccount.getWithdrawalAccountId());
        log.info("Waccount {}" , withdrawalAccount.getAccountNo());
        DeleteAccountResponse.REC deleteResponse = deleteAccountService.deleteAccount(
                "/demandDeposit/deleteDemandDepositAccount",
                financialRequestFactory.deleteAccountRequest(
                        EMAIL,
                        savingsAccount.getAccountNo(),
                        "deleteDemandDepositAccount",
                        withdrawalAccount.getAccountNo()
                )
        ).REC();

        Account depositAccount = inquireDemandDepositAccountService.findAccountByAccountNo(deleteResponse.accountNo());
        if (savingsAccount.getAmount() != null && savingsAccount.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            if (savingsAccount.isPresent()) {
                double amountToDeduct = savingsAccount.getAmount().negate().doubleValue();
                rankingService.updateRanking(
                        savingsAccount.getUserId(),
                        savingsAccount.getEntertainerId(),
                        amountToDeduct
                );
            }
        }

        entertainSavingsService.deleteByAccountId(depositAccount.getAccountId());
        deleteAccountService.deleteById(savingsAccount.getId());
    }

    public void deleteStarWithdrawalAccount(Long accountId) {
        if(entertainSavingsService.existsEntertainerAccountByWithdrawalAccountId(accountId)){
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.ENTERTAINER_SAVINGS_CONSTRAINT));
        }
        if(entertainSavingsService.existsEntertainerAccountById(accountId)){
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.ENTERTAINER_SAVINGS));
        }
        deleteAccountService.deleteById(accountId);
    }

    private REC createAccount(String email, String productUniqueNo) {
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

    private String getDummyProductUniqueNo() {
        return getProducts()
            .REC()
            .stream()
            .filter(product -> product.getAccountName().equals("test"))
            .findFirst()
            .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NOT_FOUND_DEMAND_DEPOSIT_PRODUCT)))
            .getAccountTypeUniqueNo();
    }

    public List<BankAccountResponse> findUserAccountsByBanks(List<Long> bankIds) {

        List<InquireDemandDepositAccountListResponse.REC> userAccounts = getDemandDepositListAccount("lsc7134@naver.com").getBody().REC();

        if (userAccounts == null || userAccounts.isEmpty()) {
            return List.of();
        }

        Set<String> existingAccountNos = inquireDemandDepositAccountService.findAccountByUserId(1L).stream()
            .map(Account::getAccountNo)
            .collect(Collectors.toSet());

        List<String> entertainerAccountNos = entertainSavingsService.findAllAccountNos();
        existingAccountNos.addAll(entertainerAccountNos);

        List<Bank> banks = bankService.findAllBanks();

        Map<Long, Bank> bankIdMap = banks.stream()
            .collect(Collectors.toMap(Bank::getBankId, bank -> bank));

        Map<String, Long> bankCodeToIdMap = banks.stream()
            .collect(Collectors.toMap(Bank::getBankCode, Bank::getBankId));

        return userAccounts.stream()
            .filter(account -> {
                if(existingAccountNos.contains(account.accountNo())){
                    return false;
                }
                Long bankId = bankCodeToIdMap.get(account.bankCode());
                return bankId != null && bankIds.contains(bankId);
            })
            .map(account -> {
                Bank bank = bankIdMap.get(bankCodeToIdMap.get(account.bankCode()));
                log.info(bank.toString());
                return BankAccountResponse.of(
                    account.accountNo(),
                    bank
                );
            })
            .collect(Collectors.toList());
    }

    public List<BankAccountConnectionResponse> connectUserAccounts(List<String> accountNos) {

        if (accountNos == null || accountNos.isEmpty()) {
            return List.of();
        }

        ResponseEntity<InquireDemandDepositAccountListResponse> response =
            getDemandDepositListAccount(EMAIL);

        List<InquireDemandDepositAccountListResponse.REC> allAccounts = response.getBody().REC();

        if (allAccounts == null || allAccounts.isEmpty()) {
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.NOT_FOUND_ACCOUNT));
        }

        List<Bank> banks = bankService.findAllBanks();
        Map<String, Bank> bankCodeMap = banks.stream()
            .collect(Collectors.toMap(Bank::getBankCode, bank -> bank));
        Set<String> existingAccountNos = inquireDemandDepositAccountService.findAllAccountsNo();

        List<Account> accountsToSave = new ArrayList<>();

        for (String accountNo : accountNos) {
            if(existingAccountNos.contains(accountNo)){
                continue;
            }
            Optional<InquireDemandDepositAccountListResponse.REC> accountOpt = allAccounts.stream()
                .filter(acc -> acc.accountNo().equals(accountNo))
                .findFirst();
            if (accountOpt.isPresent()) {
                InquireDemandDepositAccountListResponse.REC acc = accountOpt.get();
                Bank bank = bankCodeMap.get(acc.bankCode());

                if (bank == null) {
                    log.warn("Bank not found for code: {}", acc.bankCode());
                    continue;
                }

                // 계좌 객체 생성
                Account account = Account.of(
//                    user.getUserId(),
                    1L,
                    acc.accountNo(),
                    "KRW", // 기본 통화
                    acc.accountName(),
                    "GENERAL001", // 기본 계좌 유형
                    bank.getBankId()
                );
                accountsToSave.add(account);
            }
        }

        if(!accountsToSave.isEmpty()){
            return createAccountService.connectAll(accountsToSave).stream()
                .map(account -> {
                    Bank bank = bankService.findBankById(account.getBankId());
                    return BankAccountConnectionResponse.of(
                        account,
                        bank
                    );
                })
                .collect(Collectors.toList());
        }else{
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.DUPLICATE_ACCOUNT));
        }
    }

    public ApiCreateAccountResponse signUpWithFinancialNetwork(
        String userEmail
    ) {
        UserFinancialNetworkResponse financialNetwork = userService.requestFinancialNetwork(
            "https://finopenapi.ssafy.io/ssafy/api/v1/member",
            userEmail
        );
        userService.createUser(userEmail, financialNetwork.userKey());

        return createTestAccount(userEmail);
    }
}
