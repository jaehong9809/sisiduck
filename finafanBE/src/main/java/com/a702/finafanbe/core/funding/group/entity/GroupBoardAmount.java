package com.a702.finafanbe.core.funding.group.entity;

import com.a702.finafanbe.core.funding.group.dto.AmountDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE group_board_amounts SET deleted_at = NOW()  WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "group_board_amounts")
public class GroupBoardAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "content")
    private String content;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void prePersist() {
        this.deletedAt = null;
    }

    @Builder
    private GroupBoardAmount(
            Long boardId,
            String content,
            Long amount
    ) {
        this.boardId = boardId;
        this.content = content;
        this.amount = amount;
    }

    public static GroupBoardAmount create(AmountDto amountDto, Long boardId) {
        return GroupBoardAmount.builder()
                .boardId(boardId)
                .content(amountDto.content())
                .amount(amountDto.amount())
                .build();
    }
}
