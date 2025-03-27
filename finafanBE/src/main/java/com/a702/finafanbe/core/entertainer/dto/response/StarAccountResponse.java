package com.a702.finafanbe.core.entertainer.dto.response;

public record StarAccountResponse(
    Long userId,
    Long entertainerId,
    String accountName,
    String accountNo
) {
    public static StarAccountResponse of(
            Long userId,
            Long entertainerId,
            String accountName,
            String accountNo
    ){
        return new StarAccountResponse(
                userId,
                entertainerId,
                accountName,
                accountNo
        );
    }
}
