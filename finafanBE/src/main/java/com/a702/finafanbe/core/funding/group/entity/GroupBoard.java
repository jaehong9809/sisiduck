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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "group_boards")
@SQLDelete(sql = "UPDATE group_boards SET deleted_at = NOW() where group_board_id = ?")
public class GroupBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "funding_group_id", nullable = false)
    private Long fundingGroupId;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String imageUrl;

    private LocalDateTime deletedAt;

    @PrePersist
    protected void prePersist() {
        this.deletedAt = null;
    }

    @Builder
    private GroupBoard(Long fundingGroupId, String title, String content, Long amount, String imageUrl) {
        this.fundingGroupId = fundingGroupId;
        this.title = title;
        this.content = content;
        this.amount = amount;
        this.imageUrl = imageUrl;
    }

    public static GroupBoard create(Long fundingGroupId, String title, String content, Long amount, String imageUrl) {
        return GroupBoard.builder()
                        .fundingGroupId(fundingGroupId)
                        .title(title)
                        .content(content)
                        .amount(amount)
                        .imageUrl(imageUrl)
                        .build();
    }
}
