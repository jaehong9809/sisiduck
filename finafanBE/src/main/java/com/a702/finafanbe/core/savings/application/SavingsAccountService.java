package com.a702.finafanbe.core.savings.application;

import com.a702.finafanbe.core.bank.application.BankService;
import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.dto.response.InquireEntertainerAccountResponse;
import com.a702.finafanbe.core.entertainer.dto.response.WithdrawalAccountResponse;
import com.a702.finafanbe.core.savings.dto.request.*;
import com.a702.finafanbe.core.savings.dto.response.*;
import com.a702.finafanbe.core.savings.entity.infrastructure.SavingsAccountRepository;
import com.a702.finafanbe.core.user.application.UserService;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingsAccountService {

    private final ApiClientUtil apiClientUtil;
    private final UserService userService;
    private final BankService bankService;
    private final AccountRepository accountRepository;
    private final EntertainSavingsService entertainSavingsService;


    public RegisterSavingProductResponse createSavingsProduct(
        String path,
        RegisterSavingsProductRequest registerSavingsAccountRequest
    ) {
        ResponseEntity<RegisterSavingProductResponse> exchange = apiClientUtil.callFinancialNetwork(
            path,
            registerSavingsAccountRequest,
            RegisterSavingProductResponse.class
        );
        return exchange.getBody();
    }

    public InquireSavingAccountPaymentResponse inquireSavingsAccountPayment(
        String path,
        InquireSavingsAccountPaymentRequest inquireSavingsAccountPaymentRequest
    ) {
        ResponseEntity<InquireSavingAccountPaymentResponse> exchange = apiClientUtil.callFinancialNetwork(
            path,
            inquireSavingsAccountPaymentRequest,
            InquireSavingAccountPaymentResponse.class
        );
        return exchange.getBody();
    }

    public InquireSavingProductsResponse inquireSavingsProducts(
        String path,
        InquireSavingsProductsRequest inquireSavingsProductsRequest
    ) {

       return apiClientUtil.callFinancialNetwork(
           path,
           inquireSavingsProductsRequest,
           InquireSavingProductsResponse.class
       ).getBody();
    }

    public RegisterSavingAccountResponse createSavingsAccount(
            String path,
            RegisterSavingsAccountRequest registerSavingsAccountRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                registerSavingsAccountRequest,
                RegisterSavingAccountResponse.class
        ).getBody();
    }

    public InquireSavingAccountListResponse inquireSavingsAccountList(
            String path,
            InquireSavingAccountListRequest inquireSavingAcountListRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                inquireSavingAcountListRequest,
                InquireSavingAccountListResponse.class
        ).getBody();
    }

    public InquireSavingAccountResponse inquireSavingsAccount(
            String path,
            InquireSavingAccountRequest inquireSavingAcountRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                inquireSavingAcountRequest,
                InquireSavingAccountResponse.class
        ).getBody();
    }

    public DeleteSavingAccountResponse deleteSavingsAccount(
            String path,
            DeleteSavingAccountRequest deleteSavingAccountRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                deleteSavingAccountRequest,
                DeleteSavingAccountResponse.class
        ).getBody();
    }

    public InquireSavingExpiryInterestResponse inquireSavingsExpiryInterest(String path, InquireSavingExpiryInterestRequest inquireSavingExpiryInterestRequest) {
        return apiClientUtil.callFinancialNetwork(
                path,
                inquireSavingExpiryInterestRequest,
                InquireSavingExpiryInterestResponse.class
        ).getBody();
    }

    public InquireSavingEarlyTerminationInterestResponse inquireSavingsEarlyTerminationInterest(
            String path,
            InquireSavingEarlyTerminationInterestRequest inquireSavingEarlyTerminationInterestRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                inquireSavingEarlyTerminationInterestRequest,
                InquireSavingEarlyTerminationInterestResponse.class
        ).getBody();
    }

    public List<WithdrawalAccountResponse> getWithdrawalAccounts(String email) {
        User user = userService.findUserByEmail(email);

        return findUser(user).stream()
            .map(account -> {
                Bank bank = bankService.findBankById(account.getBankId());
                return WithdrawalAccountResponse.of(account, bank);
            })
            .collect(Collectors.toList());
    }

    private List<Account> findUser(User user) {
        return accountRepository.findByUserId(
                user.getUserId())
            .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NOT_FOUND_ACCOUNT)));
    }
}