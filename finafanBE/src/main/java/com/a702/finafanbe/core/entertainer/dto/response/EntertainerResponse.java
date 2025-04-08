package com.a702.finafanbe.core.entertainer.dto.response;

public record EntertainerResponse(
        Long entertainerId,
        String entertainerName,
        String entertainerProfileUrl,
        String fandomName
) {
    public static EntertainerResponse of(
            Long entertainerId,
            String entertainerName,
            String entertainerProfileUrl,
            String fandomName
    ){
        return new EntertainerResponse(
                entertainerId,
                entertainerName,
                entertainerProfileUrl,
                fandomName
        );
    }
}
