package com.a702.finafanbe.core.group.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Table(name = "group_user")
@SQLDelete(sql = "UPDATE group_user SET deletedAt = NOW()  WHERE group_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupUserId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private GroupUser(Long userId, Long groupId, Role role) {
        this.userId = userId;
        this.groupId = groupId;
        this.role = role;
    }

    public static GroupUser create(Long userId, Long groupId, Role role) {
        return GroupUser.builder()
                        .userId(userId)
                        .groupId(groupId)
                        .role(Role.USER)
                        .build();
    }

}