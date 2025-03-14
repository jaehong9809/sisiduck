package com.a702.finafanbe.core.user.entity;

import com.a702.finafanbe.core.user.dto.request.UserRequest;
import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "member_role", nullable = false)
    private String memberRole;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "credit_point", nullable = false)
    private String creditPoint;
    public static User of(UserRequest userRequest) {
        return new User(
                "GENERAL",
                userRequest.name(),
                userRequest.phoneNumber(),
                userRequest.birthDate(),
                userRequest.address()
        );
    }
    private User(
            String memberRole,
            String name,
            String phoneNumber,
            LocalDateTime birthDate,
            String address
    ){
        this.memberRole = memberRole;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.address = address;
    }
}
