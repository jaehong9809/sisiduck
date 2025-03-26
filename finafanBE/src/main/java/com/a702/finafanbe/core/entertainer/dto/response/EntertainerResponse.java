package com.a702.finafanbe.core.entertainer.dto.response;

public record EntertainerResponse(
        String entertainerName,
        String entertainerProfileUrl,
        String fandomName
) {
    public static EntertainerResponse of(
            String entertainerName,
            String entertainerProfileUrl,
            String fandomName
    ){
        return new EntertainerResponse(
                entertainerName,
                entertainerProfileUrl,
                fandomName
        );
    }
}
