package com.a702.finafanbe.core.user.dto.response;

import com.a702.finafanbe.core.user.entity.User;

public record UserInfoResponse(
        Long userId,
        String userName,
        String userEmail
) {
    public static UserInfoResponse from(User user){
        return new UserInfoResponse(
                user.getUserId(),
                user.getName(),
                user.getSocialEmail()
        );
    }
}
