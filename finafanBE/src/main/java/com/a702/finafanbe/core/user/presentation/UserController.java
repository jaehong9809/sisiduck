package com.a702.finafanbe.core.user.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.user.application.UserService;
import com.a702.finafanbe.core.user.dto.request.UpdateStarIdRequest;
import com.a702.finafanbe.core.user.dto.request.UserFinancialNetworkRequest;
import com.a702.finafanbe.core.user.dto.request.UserRequest;
import com.a702.finafanbe.core.user.dto.response.InquireUserResponse;
import com.a702.finafanbe.core.user.dto.response.UserFinancialNetworkResponse;
import com.a702.finafanbe.core.user.dto.response.UserResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DemandDepositFacade demandDepositFacade;

    @PostMapping
    public ResponseEntity<ResponseData<UserResponse>> signUpWithFinancialNetwork(
        @RequestBody String userEmail ) {
        userService.signUpWithFinancialNetwork(userEmail);

        return ResponseUtil.success();
    }

    @GetMapping
    public ResponseEntity<ResponseData<UserResponse>> getUser(
        String userEmail
    ) {
        return ResponseUtil.success(userService.getUserWithFinancialNetwork(userEmail));
    }

    @GetMapping("/star")
    public ResponseEntity<ResponseData<Long>> getUserStarId(
            @AuthMember User user
    ) {
        return ResponseUtil.success(userService.getUserStarId(user.getUserId()));
    }

    @PostMapping("/star")
    public ResponseEntity<ResponseData<Long>> updateUserStarId(
            @AuthMember User user,
            @RequestBody UpdateStarIdRequest updateStarIdRequest
    ) {
        return ResponseUtil.success(
            userService.updateUserStarId(user.getUserId(), updateStarIdRequest.starId()));
    }
}
