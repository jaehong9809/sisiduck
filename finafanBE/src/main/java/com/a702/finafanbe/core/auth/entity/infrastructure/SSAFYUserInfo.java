package com.a702.finafanbe.core.auth.entity.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class SSAFYUserInfo {

    @Getter
    @JsonProperty("id")
    private String socialEmail;

    @JsonProperty("ssafy_member")
    private SSAFYMember ssafyMember;

    public String getNickname(){
        return ssafyMember.ssafyProfile.nickname;
    }

    private static class SSAFYMember {
        @JsonProperty("profile")
        private SSAFYProfile ssafyProfile;
    }

    private static class SSAFYProfile {
        @JsonProperty("nickname")
        private String nickname;
        //TODO 필요하다면 프로필 추가내용 추가[주호]
    }
}
