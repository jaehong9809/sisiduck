package com.a702.finafanbe.core.ble.dto.request;

import java.util.List;

public record MatchFansUuidRequest(
        List<String> uuids
) {
}
