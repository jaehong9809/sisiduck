package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.RetrieveAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.RetrieveDemandDepositListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveDemandDepositListResponse;
import com.a702.finafanbe.global.common.header.BaseRequestHeader;
import com.a702.finafanbe.global.common.header.RequestHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inquire-demand-deposit")
@RequiredArgsConstructor
public class RetrieveDemandDepositController {

    private final RetrieveAccountService retrieveAccountService;

    @GetMapping("/demandDeposit/inquireDemandDepositList")
    public ResponseEntity<RetrieveDemandDepositListResponse> getDemandDepositList(
            @RequestParam String apiName,
            @RequestParam String transmissionDate,
            @RequestParam String transmissionTime,
            @RequestParam String institutionCode,
            @RequestParam String fintechAppNo,
            @RequestParam String apiServiceCode,
            @RequestParam String institutionTransactionUniqueNo,
            @RequestParam String apiKey
    ) {
        BaseRequestHeader retrieveDemandDepositRequestHeader = BaseRequestHeader.builder()
                .apiName(apiName)
                .transmissionDate(transmissionDate)
                .transmissionTime(transmissionTime)
                .institutionCode(institutionCode)
                .fintechAppNo(fintechAppNo)
                .apiServiceCode(apiServiceCode)
                .institutionTransactionUniqueNo(institutionTransactionUniqueNo)
                .apiKey(apiKey)
                .build();

        RetrieveDemandDepositListRequest retrieveDemandDepositRequest = new RetrieveDemandDepositListRequest(retrieveDemandDepositRequestHeader);
        return retrieveAccountService.retrieveDemandDepositList(
                "/demandDeposit/inquireDemandDepositList",
                retrieveDemandDepositRequest
        );
    }

}
