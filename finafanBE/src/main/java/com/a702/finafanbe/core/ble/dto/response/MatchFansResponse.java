package com.a702.finafanbe.core.ble.dto.response;

import com.a702.finafanbe.core.user.entity.User;

public record MatchFansResponse(
        Long userId,
        String userName,
        String imgUrl
) {
    public static MatchFansResponse of(User user, String imgUrl){
        return new MatchFansResponse(
                user.getUserId(),
                user.getName(),
                imgUrl
        );
    }
}
