package com.a702.finafanbe.core.savings.presentation;

import com.a702.finafanbe.core.savings.application.SavingsAccountService;
import com.a702.finafanbe.core.savings.dto.request.*;
import com.a702.finafanbe.core.savings.dto.response.*;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/savings")
@RequiredArgsConstructor
public class SavingsController {

    private final SavingsAccountService savingsAccountService;

    @PostMapping("/createProduct")
    public ResponseEntity<ResponseData<RegisterSavingProductResponse>> createProduct(
        @RequestBody RegisterSavingsProductRequest registerSavingsProductRequest) {
        savingsAccountService.createSavingsProduct(
            "/savings/createProduct",
            registerSavingsProductRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/inquireSavingsProducts")
    public ResponseEntity<ResponseData<InquireSavingProductsResponse>> inquirePayment(
        @RequestBody InquireSavingsProductsRequest inquireSavingsProductsRequest) {
        savingsAccountService.inquireSavingsProducts(
            "/savings/inquireSavingsProducts",
            inquireSavingsProductsRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/inquirePayment")
    public ResponseEntity<ResponseData<InquireSavingAccountPaymentResponse>> inquirePayment(
        @RequestBody InquireSavingsAccountPaymentRequest inquireSavingsAccountPaymentRequest) {
        savingsAccountService.inquireSavingsAccountPayment(
            "/savings/inquirePayment",
            inquireSavingsAccountPaymentRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/inquireExpiryInterest")
    public ResponseEntity<ResponseData<InquireSavingExpiryInterestResponse>> inquireExpiryInterest(
            @RequestBody InquireSavingExpiryInterestRequest inquireSavingExpiryInterestRequest) {
        savingsAccountService.inquireSavingsExpiryInterest(
                "/savings/inquireExpiryInterest",
                inquireSavingExpiryInterestRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/inquireEarlyTerminationInterest")
    public ResponseEntity<ResponseData<InquireSavingEarlyTerminationInterestResponse>> inquireEarlyTerminationInterest(
            @RequestBody InquireSavingEarlyTerminationInterestRequest inquireSavingEarlyTerminationInterestRequest) {
        savingsAccountService.inquireSavingsEarlyTerminationInterest(
                "/savings/inquireEarlyTerminationInterest",
                inquireSavingEarlyTerminationInterestRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<ResponseData<DeleteSavingAccountResponse>> deleteSavingsAccount(
            @RequestBody DeleteSavingAccountRequest deleteSavingAccountRequest) {
        savingsAccountService.deleteSavingsAccount(
                "/savings/deleteAccount",
                deleteSavingAccountRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/createAccount")
    public ResponseEntity<ResponseData<RegisterSavingAccountResponse>> createProduct(
            @RequestBody RegisterSavingsAccountRequest registerSavingsAccountRequest) {
        savingsAccountService.createSavingsAccount(
                "/savings/createAccount",
                registerSavingsAccountRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/inquireAccountList")
    public ResponseEntity<ResponseData<InquireSavingAccountListResponse>> inquireSavingsAccountList(
            @RequestBody InquireSavingAccountListRequest inquireSavingAcountListRequest) {
        savingsAccountService.inquireSavingsAccountList(
                "/savings/inquireAccountList",
                inquireSavingAcountListRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/inquireAccount")
    public ResponseEntity<ResponseData<InquireSavingAccountResponse>> inquireSavingsAccount(
            @RequestBody InquireSavingAccountRequest inquireSavingAcountRequest) {
        savingsAccountService.inquireSavingsAccount(
                "/savings/inquireAccount",
                inquireSavingAcountRequest
        );
        return ResponseUtil.success();
    }
}
