package com.a702.finafanbe.global.common.util;

public class RedisKeyUtil {

    public static String bleUuidKey(String uuid) {
        return "ble:uuid:" + uuid;
    }

    public static String userStarKey(Long userId) {
        return "user:" + userId + ":starId";
    }
}
