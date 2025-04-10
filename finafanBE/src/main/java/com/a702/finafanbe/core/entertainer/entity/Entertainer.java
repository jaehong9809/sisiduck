package com.a702.finafanbe.core.entertainer.entity;

import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "entertainers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Entertainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entertainerId;

    @Column(name = "entertainer_name", nullable = false, length = 100)
    private String entertainerName;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @Column(name = "entertainer_profile_url", nullable = false, length = 1024)
    private String entertainerProfileUrl;

    @Column(name = "entertainer_thumbnail_url", nullable = false)
    private String entertainerThumbnailUrl;

    @Column(name = "fandom_name", nullable = false, length = 100)
    private String fandomName;

    public void updateProfileUrl(String newProfileUrl) {
        if (newProfileUrl == null || newProfileUrl.isBlank()) {
            throw new BadRequestException.ParameterException();
        }
        this.entertainerProfileUrl = newProfileUrl;
    }
}