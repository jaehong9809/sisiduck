package com.a702.finafanbe.core.ble.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.ble.application.BleService;
import com.a702.finafanbe.core.ble.dto.request.MatchFansUuidRequest;
import com.a702.finafanbe.core.ble.dto.request.RegisterBleUuidRequest;
import com.a702.finafanbe.core.ble.dto.response.MatchFansResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ble")
@RequiredArgsConstructor
public class BleController {

    private final BleService bleService;

    @PostMapping("/uuid")
    public ResponseEntity<ResponseData<Void>> registerUuid(
            @AuthMember User user,
            @RequestBody RegisterBleUuidRequest registerBleUuidRequest
    ) {
        bleService.registerUuid(registerBleUuidRequest, user.getUserId());
        return ResponseUtil.success();
    }

    @PostMapping("/match-fans")
    public ResponseEntity<ResponseData<List<MatchFansResponse>>> matchFans(
            @AuthMember User user,
            @RequestBody MatchFansUuidRequest request
    ) {
        List<MatchFansResponse> fans = bleService.findMatchFans(request.uuids(), user.getUserId());
        return ResponseUtil.success(fans);
    }
}
