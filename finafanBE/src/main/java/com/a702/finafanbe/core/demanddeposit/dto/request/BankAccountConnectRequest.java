package com.a702.finafanbe.core.demanddeposit.dto.request;

import java.util.List;

public record BankAccountConnectRequest(
    List<Long> accountIds
) {

}
