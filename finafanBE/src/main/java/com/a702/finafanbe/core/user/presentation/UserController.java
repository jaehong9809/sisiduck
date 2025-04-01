package com.a702.finafanbe.core.user.presentation;

import com.a702.finafanbe.core.user.application.UserService;
import com.a702.finafanbe.core.user.dto.request.UserFinancialNetworkRequest;
import com.a702.finafanbe.core.user.dto.request.UserRequest;
import com.a702.finafanbe.core.user.dto.response.InquireUserResponse;
import com.a702.finafanbe.core.user.dto.response.UserFinancialNetworkResponse;
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

//    @PostMapping("/sign-up")
//    public ResponseEntity<User> signUp(UserRequest userRequest) {
//        return ResponseEntity.ok(userService.signUp(userRequest));
//    }

    @PostMapping
    public ResponseEntity<ResponseData<UserFinancialNetworkResponse>> signUpWithFinancialNetwork(
        @RequestBody String userEmail ) {
        userService.signUpWithFinancialNetwork(userEmail);
        return ResponseUtil.success();
    }

    @GetMapping
    public ResponseEntity<ResponseData<UserFinancialNetworkResponse>> getUser(
        String userEmail
    ) {
        UserFinancialNetworkResponse response = userService.getUserWithFinancialNetwork(userEmail);
        return ResponseUtil.success(response);
    }
}
