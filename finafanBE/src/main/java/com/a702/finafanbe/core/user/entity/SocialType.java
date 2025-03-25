package com.a702.finafanbe.core.user.entity;

public enum SocialType {
    SSAFY("SSAFY")
    ;
    private String description;


    SocialType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
