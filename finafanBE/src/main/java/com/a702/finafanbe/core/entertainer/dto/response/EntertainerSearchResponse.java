package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;

public record EntertainerSearchResponse(
    Long entertainerId,
    String entertainerName,
    String entertainerProfileUrl,
    String fandomName
) {
    public static EntertainerSearchResponse of(Entertainer entertainer) {
        return new EntertainerSearchResponse(
            entertainer.getEntertainerId(),
            entertainer.getEntertainerName(),
            entertainer.getEntertainerProfileUrl(),
            entertainer.getFandomName()
        );
    }
}
