package com.a702.finafanbe.core.identify.application;

import com.a702.finafanbe.core.bank.application.BankService;
import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.identify.dto.request.KRW1CertificationRequest;
import com.a702.finafanbe.core.identify.dto.request.KRW1CertificationValidateRequest;
import com.a702.finafanbe.core.identify.dto.request.KRW1Request;
import com.a702.finafanbe.core.identify.dto.request.KRW1ValidateRequest;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationResponse;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationValidateResponse;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.a702.finafanbe.global.common.financialnetwork.util.ApiConstants.*;

@Service
@RequiredArgsConstructor
public class KRW1certificationService {

    private final ApiClientUtil apiClientUtil;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;
    private final FinancialRequestFactory financialRequestFactory;
    private final BankService bankService;

    public KRW1CertificationResponse.REC registerKRW1Certification(
        String userEmail,
        KRW1Request krw1Request
    ) {
        String bankName = bankService.findBankById(krw1Request.bankId()).getBankName();
        return apiClientUtil.callFinancialNetwork(
            OPEN_AUTH_PATH,
            KRW1CertificationRequest.of(
                financialRequestFactory.createRequestHeaderWithUserKey(userEmail,extractApiName(OPEN_AUTH_PATH)),
                inquireDemandDepositAccountService.findAccountByAccountNo(krw1Request.accountNo()).getAccountNo(),
                bankName
            ),
            KRW1CertificationResponse.class
        ).getBody().REC();
    }

    public KRW1CertificationValidateResponse.REC checkKRW1Certification(
        String userEmail,
        KRW1ValidateRequest krw1ValidateRequest
    ) {
        Account account = inquireDemandDepositAccountService.findAccountById(krw1ValidateRequest.accountId());
        return apiClientUtil.callFinancialNetwork(
            VALIDATE_AUTH_PATH,
            KRW1CertificationValidateRequest.of(
                    financialRequestFactory.createRequestHeaderWithUserKey(userEmail,extractApiName(VALIDATE_AUTH_PATH)),
                    account.getAccountNo(),
                    bankService.findBankById(account.getBankId()).getBankName(),
                    krw1ValidateRequest.code()
            ),
            KRW1CertificationValidateResponse.class
        ).getBody().REC();
    }
}
