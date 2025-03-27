package com.a702.finafanbe.core.entertainer.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "entertainer_pictures")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntertainerPicture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entertainerPictureId;

    @Column(name = "entertainer_id", nullable = false)
    private Long entertainerId;

    @Column(name = "entertainer_picture_url", nullable = false)
    private String entertainerPictureUrl;
}