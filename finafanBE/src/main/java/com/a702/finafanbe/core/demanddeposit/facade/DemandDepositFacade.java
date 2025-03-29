package com.a702.finafanbe.core.demanddeposit.facade;

import com.a702.finafanbe.core.demanddeposit.dto.request.*;
import com.a702.finafanbe.core.demanddeposit.dto.response.*;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse.REC;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.request.EntertainerTransactionHistoriesRequest;
import com.a702.finafanbe.core.entertainer.dto.response.InquireEntertainerAccountResponse;
import com.a702.finafanbe.core.entertainer.dto.response.StarAccountResponse;
import com.a702.finafanbe.core.entertainer.entity.EntertainerPicture;
import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerPictureRepository;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import com.a702.finafanbe.core.user.application.UserService;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.a702.finafanbe.global.common.exception.ErrorCode.NOT_FOUND_ACCOUNT;
import static com.a702.finafanbe.global.common.exception.ErrorCode.NotFoundUser;

@Service
@RequiredArgsConstructor
public class DemandDepositFacade {

    private static final String EMAIL = "lsc7134@naver.com";

    private final FinancialRequestFactory financialRequestFactory;
    private final ApiClientUtil apiClientUtil;
    private final EntertainSavingsService entertainSavingsService;
    private final EntertainerSavingsTransactionDetailRepository entertainerSavingsTransactionDetailRepository;
    private final EntertainerSavingsAccountRepository entertainerSavingsAccountRepository;
    private final EntertainerPictureRepository entertainerPictureRepository;
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ResponseEntity<InquireDemandDepositAccountResponse> getDemandDepositAccount(
            String userEmail,
            String accountNo
    ) {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireDemandDepositAccount",
                financialRequestFactory.UserKeyAccountNoRequest(
                        userEmail,
                        accountNo,
                        "inquireDemandDepositAccount"
                ),
                InquireDemandDepositAccountResponse.class
        );
    }

    public ResponseEntity<InquireDemandDepositAccountListResponse> getDemandDepositListAccount(String userEmail) {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireDemandDepositAccountList",
                financialRequestFactory.userKeyRequest(
                        userEmail,
                        "inquireDemandDepositAccountList"
                ),
                InquireDemandDepositAccountListResponse.class
        );
    }

    public ResponseEntity<DeleteAccountResponse> deleteAccount(
            String userEmail,
            String accountNo,
            String refundAccountNo
    ) {

        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/deleteDemandDepositAccount",
                financialRequestFactory.deleteAccountRequest(
                        userEmail,
                        accountNo,
                        "deleteDemandDepositAccount",
                        refundAccountNo
                ),
                DeleteAccountResponse.class
        );
    }

    public ResponseEntity<AccountTransactionHistoriesResponse> inquireHistories(
            TransactionHistoriesRequest transactionHistoriesRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireTransactionHistoryList",
                financialRequestFactory.inquireHistories(
                        transactionHistoriesRequest.email(),
                        "inquireTransactionHistoryList",
                        transactionHistoriesRequest.accountNo(),
                        transactionHistoriesRequest.startDate(),
                        transactionHistoriesRequest.endDate(),
                        transactionHistoriesRequest.transactionType(),
                        transactionHistoriesRequest.orderByType()
                ),
                AccountTransactionHistoriesResponse.class
        );
    }

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

    public ApiCreateAccountResponse createAccount(
            String email,
            String productName
    ) {
        User user = userRepository.findBySocialEmail(email)
            .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(NotFoundUser)));
        RetrieveProductsResponse.REC rec = getProducts().getBody()
                .REC()
                .stream()
                .filter(product -> product.getAccountName().equals(productName))
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

    public ResponseEntity<RetrieveProductsResponse> getProducts() {
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireDemandDepositList",
                financialRequestFactory.inquireProducts(
                        "inquireDemandDepositList"
                ),
                RetrieveProductsResponse.class
        );
    }

    public StarAccountResponse createEntertainerSavings(CreateStarAccountRequest createStarAccountRequest){
        Account withdrawalAccount = findAccount(createStarAccountRequest.withdrawalAccountId());
        ApiCreateAccountResponse response = createAccount(
            EMAIL,
            createStarAccountRequest.productName()
        );
        return entertainSavingsService.createEntertainerSavings(
                createStarAccountRequest,
                response,
                withdrawalAccount.getAccountNo()
        );
    }


    public InquireEntertainerAccountResponse getEntertainerAccount(
            String userEmail,
            Long savingAccountId
    ) {
        EntertainerSavingsAccount entertainerSavingsAccount = entertainerSavingsAccountRepository.findByDepositAccountId(savingAccountId);
        Account depositAccount = findAccount(entertainerSavingsAccount.getDepositAccountId());
        InquireDemandDepositAccountResponse.REC rec = apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireDemandDepositAccount",
                financialRequestFactory.UserKeyAccountNoRequest(
                        userEmail,
                        depositAccount.getAccountNo(),
                        "inquireDemandDepositAccount"
                ),
                InquireDemandDepositAccountResponse.class
        ).getBody().REC();
        String accountNo = rec.accountNo();

        Account account = accountRepository.findByAccountNo(accountNo).orElseThrow(() -> new BadRequestException(ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
        EntertainerSavingsAccount savingsAccount = entertainerSavingsAccountRepository.findByDepositAccountId(account.getAccountId());
        EntertainerPicture entertainerPicture = entertainerPictureRepository.findByEntertainerId(savingsAccount.getEntertainerId());
        return InquireEntertainerAccountResponse.of(
                rec.bankCode(),
                rec.bankName(),
                rec.accountNo(),
                rec.accountName(),
                rec.dailyTransferLimit(),
                rec.oneTimeTransferLimit(),
                rec.accountBalance(),
                rec.lastTransactionDate(),
                rec.currency(),
                entertainerPicture.getEntertainerPictureUrl()
        );
    }

    public InquireEntertainerHistoriesResponse inquireEntertainerHistories(
            EntertainerTransactionHistoriesRequest entertainerTransactionHistoriesRequest
    ) {
        AccountTransactionHistoriesResponse.REC inquireTransactionHistoryList = apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireTransactionHistoryList",
                financialRequestFactory.inquireHistories(
                        EMAIL,
                        "inquireTransactionHistoryList",
                        entertainerTransactionHistoriesRequest.accountNo(),
                        "00000101",
                        DateUtil.getTransmissionDate(),
                        "A",
                        entertainerTransactionHistoriesRequest.orderByType()
                ),
                AccountTransactionHistoriesResponse.class
        ).getBody().REC();
        List<EntertainerSavingsTransactionDetail> transactionDetails = entertainerSavingsTransactionDetailRepository.findByDepositAccountNo(entertainerTransactionHistoriesRequest.accountNo());

        Map<Long, String> transactionImageMap = transactionDetails.stream().collect(
                Collectors.toMap(
                        EntertainerSavingsTransactionDetail::getTransactionUniqueNo,
                        EntertainerSavingsTransactionDetail::getImageUrl,
                        (existingValue, newValue) -> existingValue
                )
        );

        List<TransactionWithImageResponse> transactionsWithImages = inquireTransactionHistoryList.list().stream()
                .map(transaction -> {
                    String imageUrl = transactionImageMap.getOrDefault(transaction.transactionUniqueNo(), null);
                    return new TransactionWithImageResponse(
                            transaction.transactionUniqueNo(),
//                            transaction.transactionDate(),
//                            transaction.transactionTime(),
//                            transaction.transactionType(),
//                            transaction.transactionTypeName(),
//                            transaction.transactionAccountNo(),
                            transaction.transactionBalance(),
                            transaction.transactionAfterBalance(),
//                            transaction.transactionSummary(),
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
            EntertainerSavingsAccount entertainerSavingsAccount = entertainerSavingsAccountRepository.findById(depositAccountId).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
            Account depositAccount = findAccount(entertainerSavingsAccount.getDepositAccountId());
            Account withdrawalAccount = findAccount(entertainerSavingsAccount.getWithdrawalAccountId());
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

    private Account findAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
    }
}
