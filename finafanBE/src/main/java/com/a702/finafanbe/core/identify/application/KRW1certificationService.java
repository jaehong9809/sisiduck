package com.a702.finafanbe.core.identify.application;

import com.a702.finafanbe.core.identify.dto.request.KRW1CertificationRequest;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationResponse;
import com.a702.finafanbe.core.identify.entity.infrastructure.KRW1certificationRepository;
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

    private final RestTemplate restTemplate;
    private final KRW1certificationRepository krw1certificationRepository;

    public ResponseEntity<KRW1CertificationResponse> registerKRW1Certification(KRW1CertificationRequest krw1CertificationRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<KRW1CertificationRequest> httpEntity = new HttpEntity<>(
            krw1CertificationRequest,
            headers
        );
        ResponseEntity<KRW1CertificationResponse> exchange = restTemplate.exchange(
            "https://finopenapi.ssafy.io/ssafy/api/v1/edu/accountAuth/openAccountAuth",
            HttpMethod.POST,
            httpEntity,
            KRW1CertificationResponse.class
        );

        return ResponseEntity.ok(exchange.getBody());
    }
}
