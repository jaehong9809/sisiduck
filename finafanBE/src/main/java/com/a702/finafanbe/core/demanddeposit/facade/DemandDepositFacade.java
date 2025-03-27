package com.a702.finafanbe.core.demanddeposit.facade;

import com.a702.finafanbe.core.demanddeposit.dto.request.DepositRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransactionHistoriesRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransactionHistoryRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransferRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.*;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.response.InquireEntertainerAccountResponse;
import com.a702.finafanbe.core.entertainer.dto.response.StarAccountResponse;
import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandDepositFacade {

    private final FinancialRequestFactory financialRequestFactory;
    private final ApiClientUtil apiClientUtil;
    private final EntertainSavingsService entertainSavingsService;
    private final EntertainerSavingsTransactionDetailRepository entertainerSavingsTransactionDetailRepository;

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

    public ResponseEntity<CreateAccountResponse> createAccount(
            String email,
            String productName
    ) {
        RetrieveProductsResponse.REC rec = getProducts().getBody()
                .REC()
                .stream()
                .filter(product -> product.getAccountName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NOT_FOUND_DEMAND_DEPOSIT_PRODUCT)));
        return apiClientUtil.callFinancialNetwork(
                "/demandDeposit/createDemandDepositAccount",
                financialRequestFactory.UserKeyAccountTypeUniqueNoRequest(
                        email,
                        rec.getAccountTypeUniqueNo(),
                        "createDemandDepositAccount"
                ),
                CreateAccountResponse.class
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
        return entertainSavingsService.createEntertainerSavings(
                createStarAccountRequest,
                createAccount(
                        createStarAccountRequest.userEmail(),
                        createStarAccountRequest.accountName()
                ).getBody().REC().getAccountNo());
    }


    public InquireEntertainerAccountResponse getEntertainerAccount(String userEmail, String accountNo) {
        InquireDemandDepositAccountResponse.REC rec = apiClientUtil.callFinancialNetwork(
                "/demandDeposit/inquireDemandDepositAccount",
                financialRequestFactory.UserKeyAccountNoRequest(
                        userEmail,
                        accountNo,
                        "inquireDemandDepositAccount"
                ),
                InquireDemandDepositAccountResponse.class
        ).getBody().REC();
        EntertainerSavingsTransactionDetail returnValue = entertainerSavingsTransactionDetailRepository.findByDepositAccountNo(rec.accountNo());
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
                returnValue.getImageUrl()
        );
    }

    public InquireEntertainerHistoriesResponse inquireEntertainerHistories(TransactionHistoriesRequest transactionHistoriesRequest) {
        AccountTransactionHistoriesResponse.REC inquireTransactionHistoryList = apiClientUtil.callFinancialNetwork(
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
        ).getBody().REC();
        EntertainerSavingsTransactionDetail returnValue = entertainerSavingsTransactionDetailRepository.findByDepositAccountNo(transactionHistoriesRequest.accountNo());
        return InquireEntertainerHistoriesResponse.of(
                inquireTransactionHistoryList.totalCount(),
                inquireTransactionHistoryList.list(),
                returnValue.getImageUrl()
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
}
