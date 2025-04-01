package com.a702.finafanbe.core.funding.group.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "group_users")
@SQLDelete(sql = "UPDATE group_users SET deleted_at = NOW()  WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "funding_group_id", nullable = false)
    private Long fundingGroupId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    private GroupUser(Long userId, Long fundingGroupId, Role role) {
        this.userId = userId;
        this.fundingGroupId = fundingGroupId;
        this.role = role;
    }

    public static GroupUser create(Long userId, Long fundingGroupId, Role role) {
        return GroupUser.builder()
                        .userId(userId)
                        .fundingGroupId(fundingGroupId)
                        .role(role)
                        .build();
    }
}