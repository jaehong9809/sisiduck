package com.a702.finafanbe.core.identify.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.identify.application.KRW1certificationService;
import com.a702.finafanbe.core.identify.dto.request.KRW1Request;
import com.a702.finafanbe.core.identify.dto.request.KRW1ValidateRequest;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationResponse;
import com.a702.finafanbe.core.identify.dto.response.KRW1CertificationValidateResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-auth")
public class KRW1certificationController {
    private final KRW1certificationService krw1certificationService;

    @PostMapping("/certification")
    public ResponseEntity<ResponseData<KRW1CertificationResponse.REC>> registerKRW1Certification(
            @AuthMember User user,
            @RequestBody KRW1Request krw1Certification
    ) {
        return ResponseUtil.success(krw1certificationService.registerKRW1Certification(user.getSocialEmail(), krw1Certification));
    }

    @PostMapping("/verification")
    public ResponseEntity<ResponseData<KRW1CertificationValidateResponse.REC>> checkKRW1Certification(
            @AuthMember User user,
            @RequestBody KRW1ValidateRequest krw1ValidateRequest
        ) {
        return ResponseUtil.success(krw1certificationService.checkKRW1Certification(user.getSocialEmail(), krw1ValidateRequest));
    }
}
