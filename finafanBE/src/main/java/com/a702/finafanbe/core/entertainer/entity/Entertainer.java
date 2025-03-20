package com.a702.finafanbe.core.entertainer.entity;

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

    @Column(name = "entertainer_age", nullable = false)
    private int entertainerAge;

    @Column(name = "entertaincer_profile_url", nullable = false, length = 1024)
    private String entertainerProfileUrl;

    @Column(name = "fandom_name", nullable = false, length = 100)
    private String fandomName;
}