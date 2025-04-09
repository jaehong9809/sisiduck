package com.a702.finafanbe.core.funding.group.entity;

import com.a702.finafanbe.core.funding.group.dto.UpdateGroupBoardRequest;
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
@SQLDelete(sql = "UPDATE group_boards SET deleted_at = NOW() where id = ?")
public class GroupBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "funding_id", nullable = false)
    private Long fundingId;

    @Column(nullable = false)
    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void prePersist() {
        this.deletedAt = null;
    }

    @Builder
    private GroupBoard(Long fundingId, String content) {
        this.fundingId = fundingId;
        this.content = content;
    }

    public static GroupBoard create(Long fundingId, String content) {
        return GroupBoard.builder()
                        .fundingId(fundingId)
                        .content(content)
                        .build();
    }

    public void update(String content) {
        this.content = content;
    }
}
