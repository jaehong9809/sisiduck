package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.RetrieveAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.RetrieveProductsRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveProductsResponse;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;
import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class RetrieveDemandDepositController {

    private final FinancialNetworkUtil financialNetworkUtil;
    private final RetrieveAccountService retrieveAccountService;

    @GetMapping("/demandDeposit/inquireDemandDepositList")
    public ResponseEntity<RetrieveProductsResponse> getDemandDepositList(
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

        RetrieveProductsRequest retrieveDemandDepositRequest = new RetrieveProductsRequest(retrieveDemandDepositRequestHeader);
        return retrieveAccountService.retrieveDemandDepositList(
                "/demandDeposit/inquireDemandDepositList",
                retrieveDemandDepositRequest
        );
    }

}
