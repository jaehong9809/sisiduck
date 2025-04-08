package com.a702.finafanbe.core.demanddeposit.dto.request;

public record ApiCreateAccountResponse(
        Long userId,
        String userEmail,
        String accountNo,
        String bankCode,
        String currency,
        String accountTypeUniqueNo
) {
    public static ApiCreateAccountResponse of(
            Long userId,
            String userEmail,
            String accountNo,
            String bankCode,
            String currency,
            String accountTypeUniqueNo
    ){
        return new ApiCreateAccountResponse(
                userId,
                userEmail,
                accountNo,
                bankCode,
                currency,
                accountTypeUniqueNo
        );
    }
}
