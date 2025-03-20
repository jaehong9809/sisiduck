package com.a702.finafanbe.core.user.application;

import com.a702.finafanbe.core.user.dto.request.UserRequest;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException.*;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User signUp(UserRequest userRequest) {
        getUser(userRequest);
        return userRepository.save(User.of(userRequest));
    }

    private void getUser(UserRequest userRequest) {
        userRepository.findByPhoneNumber(userRequest.phoneNumber()).orElseThrow(
                ParameterException::new);
    }
}
