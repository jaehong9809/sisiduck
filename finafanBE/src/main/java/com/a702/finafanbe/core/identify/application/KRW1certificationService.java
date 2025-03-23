package com.a702.finafanbe.core.identify.application;

import com.a702.finafanbe.core.identify.dto.request.KRW1CertificationRequest;
import com.a702.finafanbe.core.identify.dto.request.KRW1CertificationValidateRequest;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationResponse;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationValidateResponse;
import com.a702.finafanbe.core.identify.entity.infrastructure.KRW1certificationRepository;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KRW1certificationService {

    private final ApiClientUtil apiClientUtil;
    private final KRW1certificationRepository krw1certificationRepository;

    public ResponseEntity<KRW1CertificationResponse> registerKRW1Certification(
        String path,
        KRW1CertificationRequest krw1CertificationRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
            path,
            krw1CertificationRequest,
            KRW1CertificationResponse.class
        );
    }

    public ResponseEntity<KRW1CertificationValidateResponse> checkKRW1Certification(
        String path,
        KRW1CertificationValidateRequest krw1Certification) {
        return apiClientUtil.callFinancialNetwork(
            path,
            krw1Certification,
            KRW1CertificationValidateResponse.class
        );
    }
}
