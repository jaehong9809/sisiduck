package com.a702.finafanbe.core.savings.presentation;

import com.a702.finafanbe.core.savings.application.SavingsAccountService;
import com.a702.finafanbe.core.savings.dto.request.*;
import com.a702.finafanbe.core.savings.dto.response.*;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/savings")
@RequiredArgsConstructor
public class SavingsController {

    private final SavingsAccountService savingsAccountService;

    @PostMapping("/product")
    public ResponseEntity<ResponseData<RegisterSavingProductResponse>> createProduct(
        @RequestBody RegisterSavingsProductRequest registerSavingsProductRequest) {
        savingsAccountService.createSavingsProduct(
            "/savings/createProduct",
            registerSavingsProductRequest
        );
        return ResponseUtil.success();
    }

    @GetMapping("/products")
    public ResponseEntity<ResponseData<InquireSavingProductsResponse>> inquirePayment(
        @RequestBody InquireSavingsProductsRequest inquireSavingsProductsRequest) {
        savingsAccountService.inquireSavingsProducts(
            "/savings/inquireSavingsProducts",
            inquireSavingsProductsRequest
        );
        return ResponseUtil.success();
    }

    @GetMapping("/payment-count")
    public ResponseEntity<ResponseData<InquireSavingAccountPaymentResponse>> inquirePayment(
        @RequestBody InquireSavingsAccountPaymentRequest inquireSavingsAccountPaymentRequest) {
        savingsAccountService.inquireSavingsAccountPayment(
            "/savings/inquirePayment",
            inquireSavingsAccountPaymentRequest
        );
        return ResponseUtil.success();
    }

    @GetMapping("/expiry-interest")
    public ResponseEntity<ResponseData<InquireSavingExpiryInterestResponse>> inquireExpiryInterest(
            @RequestBody InquireSavingExpiryInterestRequest inquireSavingExpiryInterestRequest) {
        savingsAccountService.inquireSavingsExpiryInterest(
                "/savings/inquireExpiryInterest",
                inquireSavingExpiryInterestRequest
        );
        return ResponseUtil.success();
    }

    @GetMapping("/early-termination-interest")
    public ResponseEntity<ResponseData<InquireSavingEarlyTerminationInterestResponse>> inquireEarlyTerminationInterest(
            @RequestBody InquireSavingEarlyTerminationInterestRequest inquireSavingEarlyTerminationInterestRequest) {
        savingsAccountService.inquireSavingsEarlyTerminationInterest(
                "/savings/inquireEarlyTerminationInterest",
                inquireSavingEarlyTerminationInterestRequest
        );
        return ResponseUtil.success();
    }

    @DeleteMapping("/account")
    public ResponseEntity<ResponseData<DeleteSavingAccountResponse>> deleteSavingsAccount(
            @RequestBody DeleteSavingAccountRequest deleteSavingAccountRequest) {
        savingsAccountService.deleteSavingsAccount(
                "/savings/deleteAccount",
                deleteSavingAccountRequest
        );
        return ResponseUtil.success();
    }

    @PostMapping("/account")
    public ResponseEntity<ResponseData<RegisterSavingAccountResponse>> createAccount(
            @RequestBody RegisterSavingsAccountRequest registerSavingsAccountRequest) {
        savingsAccountService.createSavingsAccount(
                "/savings/createAccount",
                registerSavingsAccountRequest
        );
        return ResponseUtil.success();
    }

    @GetMapping("/accounts")
    public ResponseEntity<ResponseData<InquireSavingAccountListResponse>> inquireSavingsAccountList(
            @RequestBody InquireSavingAccountListRequest inquireSavingAccountListRequest) {
        savingsAccountService.inquireSavingsAccountList(
                "/savings/inquireAccountList",
                inquireSavingAccountListRequest
        );
        return ResponseUtil.success();
    }

    @GetMapping("/account")
    public ResponseEntity<ResponseData<InquireSavingAccountResponse>> inquireSavingsAccount(
            @RequestBody InquireSavingAccountRequest inquireSavingAccountRequest) {
        savingsAccountService.inquireSavingsAccount(
                "/savings/inquireAccount",
                inquireSavingAccountRequest
        );
        return ResponseUtil.success();
    }
}
