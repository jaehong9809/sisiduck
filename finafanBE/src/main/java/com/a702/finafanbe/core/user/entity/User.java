package com.a702.finafanbe.core.user.entity;

import com.a702.finafanbe.core.user.dto.request.UserRequest;
import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user SET delete_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at = false")
public class User extends BaseEntity {
    private static final int MAX_SOCIAL_EMAIL_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "member_role", nullable = false)
    private String memberRole;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Size(max = MAX_SOCIAL_EMAIL_LENGTH)
    @Column(name = "social_email")
    private String socialEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;

    @Column(name = "userKey", nullable = false)
    private String userKey;

    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "credit_point", nullable = false)
    private String creditPoint;

    @Column(name = "entertainer_id")
    private Long entertainerId;

    public void updateFavoriteEntertainer(Long entertainerId) {
        this.entertainerId = entertainerId;
    }

    public static User of(UserRequest userRequest) {
        return new User(
                "GENERAL",
                userRequest.name(),
                userRequest.phoneNumber(),
                userRequest.birthDate(),
                userRequest.address()
        );
    }

    public static User of(String socialEmail, String nickname) {
        return new User(
                socialEmail,
                nickname
        );
    }

    public static User of(
            String socialEmail,
            String userKey,
            String socialType) {
        return new User(
                socialEmail,
                userKey,
                socialType
        );
    }

    private User(
            String socialEmail,
            String userKey,
            String socialType
    ){
        this.socialEmail = socialEmail;
        this.userKey = userKey;
        this.socialType = SocialType.valueOf(socialType);
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

    private User(
            String socialEmail,
            String nickname
    ){
        this.socialEmail = socialEmail;
        this.name = nickname;
    }
}
