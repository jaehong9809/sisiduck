package com.a702.finafanbe.core.bank.dto.request;

import java.util.List;

public record AccountConnectionRequest(
    List<String> accountNos
) {
}
