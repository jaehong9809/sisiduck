package com.a702.finafanbe.core.auth.entity.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SSAFYUserInfo {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    public String toString(){
        return "userId: " + userId + ",\n email: " + email + ",\n name: " + name;
    }
}
