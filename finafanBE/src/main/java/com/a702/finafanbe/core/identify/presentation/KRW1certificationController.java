package com.a702.finafanbe.core.identify.presentation;

import com.a702.finafanbe.core.identify.application.KRW1certificationService;
import com.a702.finafanbe.core.identify.dto.request.KRW1CertificationRequest;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/krw1-cert")
public class KRW1certificationController {
    private final KRW1certificationService krw1certificationService;

    @PostMapping
    public ResponseEntity<KRW1CertificationResponse> registerKRW1Certification(@RequestBody KRW1CertificationRequest krw1Certification) {
        return krw1certificationService.registerKRW1Certification(krw1Certification);
    }
}
