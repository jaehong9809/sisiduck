package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.RetrieveAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.RetrieveDemandDepositListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveDemandDepositListResponse;
import com.a702.finafanbe.global.common.financialnetwork.entity.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;
import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inquire-demand-deposit")
@RequiredArgsConstructor
public class RetrieveDemandDepositController {

    private final FinancialNetworkUtil financialNetworkUtil;
    private final RetrieveAccountService retrieveAccountService;

    @GetMapping("/demandDeposit/inquireDemandDepositList")
    public ResponseEntity<RetrieveDemandDepositListResponse> getDemandDepositList(
//            @AuthMember User userId
    ) {
        BaseRequestHeader retrieveDemandDepositRequestHeader = BaseRequestHeader.builder()
                .apiName("inquireDemandDepositList")
                .transmissionDate(DateUtil.getTransmissionDate())
                .transmissionTime(DateUtil.getTransmissionTime())
                .institutionCode(financialNetworkUtil.getInstitutionCode())
                .fintechAppNo(financialNetworkUtil.getFintechAppNo())
                .apiServiceCode("inquireDemandDepositList")
                .institutionTransactionUniqueNo(financialNetworkUtil.getInstitutionTransactionUniqueNo())
                .apiKey(financialNetworkUtil.getApiKey())
                .build();

        RetrieveDemandDepositListRequest retrieveDemandDepositRequest = new RetrieveDemandDepositListRequest(retrieveDemandDepositRequestHeader);
        return retrieveAccountService.retrieveDemandDepositList(
                "/demandDeposit/inquireDemandDepositList",
                retrieveDemandDepositRequest
        );
    }

}
