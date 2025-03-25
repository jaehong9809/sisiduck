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
@Table(name = "group_boards")
@SQLDelete(sql = "UPDATE group_boards SET deleted_at = NOW() where group_board_id = ?")
public class GroupBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupBoardId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private Long amount;

    private String imageUrl;

    private LocalDateTime deletedAt;

    @PrePersist
    protected void prePersist() {
        this.deletedAt = null;
    }

    @Builder
    private GroupBoard(Long groupId, String title, String content, Long amount, String imageUrl) {
        this.groupId = groupId;
        this.title = title;
        this.content = content;
        this.amount = amount;
        this.imageUrl = imageUrl;
    }

    public static GroupBoard create(Long groupId, String title, String content, Long amount, String imageUrl) {
        return GroupBoard.builder()
                        .groupId(groupId)
                        .title(title)
                        .content(content)
                        .amount(amount)
                        .imageUrl(imageUrl)
                        .build();
    }
}
