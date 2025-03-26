package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireAccountBalanceRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireAccountHolderNameRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireAccountBalanceResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireAccountHolderNameResponse;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.entity.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class InquireDemandDepositAccountController {

    private final FinancialNetworkUtil financialNetworkUtil;
    private final UserRepository userRepository;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;

    @GetMapping("/account")
    public ResponseEntity<InquireDemandDepositAccountResponse> getDemandDepositAccount(
            String userEmail,
            String accountNo
    ) {
        InquireDemandDepositAccountRequest inquireDemandDepositAccountRequest = new InquireDemandDepositAccountRequest(
                BaseRequestHeaderIncludeUserKey.builder()
                        .apiName("inquireDemandDepositAccount")
                        .transmissionDate(DateUtil.getTransmissionDate())
                        .transmissionTime(DateUtil.getTransmissionTime())
                        .institutionCode(financialNetworkUtil.getInstitutionCode())
                        .fintechAppNo(financialNetworkUtil.getFintechAppNo())
                        .apiServiceCode("inquireDemandDepositAccount")
                        .institutionTransactionUniqueNo(financialNetworkUtil.getInstitutionTransactionUniqueNo())
                        .apiKey(financialNetworkUtil.getApiKey())
                        .userKey(userRepository.findBySocialEmail(userEmail).orElseThrow(()->new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundUser))).getUserKey())
                        .build(),
                accountNo
        );
        return inquireDemandDepositAccountService.retrieveDemandDepositAccount(
                "/demandDeposit/inquireDemandDepositAccount",
                inquireDemandDepositAccountRequest
        );
    }

    @GetMapping("/accounts")
    public ResponseEntity<InquireDemandDepositAccountListResponse> getDemandDepositAccountList(
            String userEmail,
            String accountNo
    ) {
        InquireDemandDepositAccountListRequest inquireDemandDepositAccountListRequest = new InquireDemandDepositAccountListRequest(
                BaseRequestHeaderIncludeUserKey.builder()
                        .apiName("inquireDemandDepositAccountList")
                        .transmissionDate(DateUtil.getTransmissionDate())
                        .transmissionTime(DateUtil.getTransmissionTime())
                        .institutionCode(financialNetworkUtil.getInstitutionCode())
                        .fintechAppNo(financialNetworkUtil.getFintechAppNo())
                        .apiServiceCode("inquireDemandDepositAccountList")
                        .institutionTransactionUniqueNo(financialNetworkUtil.getInstitutionTransactionUniqueNo())
                        .apiKey(financialNetworkUtil.getApiKey())
                        .userKey(userRepository.findBySocialEmail(userEmail).orElseThrow(()->new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundUser))).getUserKey())
                        .build()
        );
        return inquireDemandDepositAccountService.retrieveDemandDepositAccountList(
                "/demandDeposit/inquireDemandDepositAccountList",
                inquireDemandDepositAccountListRequest
        );
    }

    @GetMapping("/account-holder")
    public ResponseEntity<InquireAccountHolderNameResponse> inquireAccountHolderName(@RequestBody InquireAccountHolderNameRequest inquireAccountHolderNameRequest){
        return inquireDemandDepositAccountService.inquireAccountHolderName(
                "/demandDeposit/inquireDemandDepositAccountHolderName",
                inquireAccountHolderNameRequest
        );
    }

    @GetMapping("/account-balance")
    public ResponseEntity<InquireAccountBalanceResponse> inquireAccountBalance(@RequestBody InquireAccountBalanceRequest inquireAccountBalanceRequest){
        return inquireDemandDepositAccountService.inquireBalanceName(
                "/demandDeposit/inquireDemandDepositAccountBalance",
                inquireAccountBalanceRequest
        );
    }
}
