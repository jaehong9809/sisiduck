package com.a702.finafanbe.core.user.presentation;

import com.a702.finafanbe.core.user.application.UserService;
import com.a702.finafanbe.core.user.dto.request.UserRequest;
import com.a702.finafanbe.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(UserRequest userRequest) {
        return ResponseEntity.ok(userService.signUp(userRequest));
    }
}
