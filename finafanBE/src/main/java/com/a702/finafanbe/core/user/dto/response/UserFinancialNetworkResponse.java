package com.a702.finafanbe.core.user.dto.response;

public record UserFinancialNetworkResponse(
    String userId,
    String userName,
    String institutionCode,
    String userKey,
    String created,
    String modified
) {

}
