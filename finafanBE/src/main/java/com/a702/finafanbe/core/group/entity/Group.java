package com.a702.finafanbe.core.group.entity;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "groups")
@SQLDelete(sql = "UPDATE group SET deleted_at = NOW() WHERE group_id = ?")
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(name = "entertainer_id")
    private Long entertainerId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_description")
    private String groupDescription;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    private void prePersist() {
        this.deletedAt = null;
    }

    @Builder
    private Group(Long entertainerId, String groupName, String groupDescription) {
        this.entertainerId = entertainerId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
    }

    public static Group create(Long entertainerId, String groupName, String groupDescription) {
        return Group.builder()
                    .entertainerId(entertainerId)
                    .groupName(groupName)
                    .groupDescription(groupDescription)
                    .build();
    }

}