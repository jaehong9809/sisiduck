package com.a702.finafanbe.core.funding.funding.dto;

public record EntertainerResponse (
        Long entertainerId,
        String name,
        String imageUrl
) {
    public static EntertainerResponse of (
            Long entertainerId,
            String name,
            String imageUrl
    ) {
        return new EntertainerResponse(
                entertainerId,
                name,
                imageUrl
        );
    }
}
