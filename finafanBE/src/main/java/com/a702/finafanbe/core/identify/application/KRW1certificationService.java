package com.a702.finafanbe.core.identify.application;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.identify.dto.request.KRW1CertificationRequest;
import com.a702.finafanbe.core.identify.dto.request.KRW1CertificationValidateRequest;
import com.a702.finafanbe.core.identify.dto.request.KRW1Request;
import com.a702.finafanbe.core.identify.dto.request.KRW1ValidateRequest;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationResponse;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationValidateResponse;
import com.a702.finafanbe.core.identify.entity.infrastructure.KRW1certificationRepository;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.a702.finafanbe.global.common.financialnetwork.util.ApiConstants.*;

@Service
@RequiredArgsConstructor
public class KRW1certificationService {

    private static final String EMAIL = "lsc7134@naver.com";

    private final ApiClientUtil apiClientUtil;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;
    private final KRW1certificationRepository krw1certificationRepository;
    private final FinancialRequestFactory financialRequestFactory;

    public KRW1CertificationResponse.REC registerKRW1Certification(
        KRW1Request krw1Request
    ) {
        return apiClientUtil.callFinancialNetwork(
            OPEN_AUTH_PATH,
            KRW1CertificationRequest.of(
                financialRequestFactory.createRequestHeaderWithUserKey(EMAIL,extractApiName(OPEN_AUTH_PATH)),
                inquireDemandDepositAccountService.findAccountById(krw1Request.accountId()).getAccountNo(),
                "SISIDUCK"
            ),
            KRW1CertificationResponse.class
        ).getBody().REC();
    }

    public KRW1CertificationValidateResponse.REC checkKRW1Certification(
        KRW1ValidateRequest krw1ValidateRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
            VALIDATE_AUTH_PATH,
            KRW1CertificationValidateRequest.of(
                    financialRequestFactory.createRequestHeaderWithUserKey(EMAIL,extractApiName(VALIDATE_AUTH_PATH)),
                    inquireDemandDepositAccountService.findAccountById(krw1ValidateRequest.accountId()).getAccountNo(),
                    "SISIDUCK",
                    krw1ValidateRequest.code()
            ),
            KRW1CertificationValidateResponse.class
        ).getBody().REC();
    }
}
