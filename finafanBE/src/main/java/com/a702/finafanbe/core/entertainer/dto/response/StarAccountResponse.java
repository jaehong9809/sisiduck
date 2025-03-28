package com.a702.finafanbe.core.entertainer.dto.response;

public record StarAccountResponse(
    Long userId,
    Long entertainerId,
    Long depositAccountId,
    Long withdrawalAccountId
) {
    public static StarAccountResponse of(
            Long userId,
            Long entertainerId,
            Long depositAccountId,
            Long withdrawalAccountId
    ){
        return new StarAccountResponse(
                userId,
                entertainerId,
                depositAccountId,
                withdrawalAccountId
        );
    }
}
