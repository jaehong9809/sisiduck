package com.a702.finafanbe.global.common.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt = new Date(253402214400000L);

    public boolean isDeleted(){
        return this.deletedAt.getTime() < new Date(253402214400000L).getTime();
    }

    public void softDelete(){
        this.deletedAt = new Date();
    }
}
