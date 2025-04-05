package com.a702.finafanbe.core.bank.dto.request;

import java.util.List;

public record BankAccountsRequest(
    List<Long> bankIds
){

}
