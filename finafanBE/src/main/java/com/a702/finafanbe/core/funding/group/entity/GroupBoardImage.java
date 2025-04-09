package com.a702.finafanbe.core.funding.group.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE group_board_images SET deleted_at = NOW()  WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "group_board_images")
public class GroupBoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

    private String imageUrl;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void prePersist() {
        this.deletedAt = null;
    }

    private GroupBoardImage(Long boardId, String imageUrl) {
        this.boardId = boardId;
        this.imageUrl = imageUrl;
    }

    public static GroupBoardImage create(String imageUrl, Long boardId) {
        return new GroupBoardImage(boardId, imageUrl);
    }
}
