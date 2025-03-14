package com.a702.finafanbe.core.user.dto.request;

import java.time.LocalDateTime;

public record UserRequest(
    String name,
    String phoneNumber,
    LocalDateTime birthDate,
    String address,
    String password
) {
    public static UserRequest of(
            String name,
            String phoneNumber,
            LocalDateTime birthDate,
            String address,
            String password
    ) {
        return new UserRequest(
            name,
            phoneNumber,
            birthDate,
            address,
            password
        );
    }
}
