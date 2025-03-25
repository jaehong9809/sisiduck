package com.a702.finafanbe.core.user.dto.response;

public record InquireUserResponse(
    String userId,
    String username,
    String institutionCode,
    String userKey,
    String created,
    String modified
) {

}
