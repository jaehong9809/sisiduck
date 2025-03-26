package com.a702.finafanbe.core.entertainer.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStartRequest;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/star")
@RequiredArgsConstructor
public class EntertainSavingsController {

    private final EntertainSavingsService entertainService;

    @PostMapping("/select")
    public ResponseEntity<ResponseData<EntertainerResponse>> selectStar(
            @AuthMember User user,
            String entertainerName
    ){
        return ResponseUtil.success(entertainService.choiceStar(
                user,
                entertainerName
        ));
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<Entertainer>>> getStars(){
        return ResponseUtil.success(entertainService.findStars());
    }

    //TODO 검색.

    @PostMapping("/createSavings")
    public ResponseEntity<ResponseData<Void>> createSavings(
            @AuthMember User user,
            SelectStartRequest selectStartRequest
    ){
        entertainService.createEntertainerSavings(
                user,
                selectStartRequest
        );
        return ResponseUtil.success();
    }

    //TODO 1.수시입출금하기 + 응원메시지 + 출금은 안되게 처리하기 ㅗ
}
