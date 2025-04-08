package com.a702.finafan.data.savings.dto.request

data class BankIdsRequest(
    val bankIds: List<Long> = emptyList()
)

data class AccountNosRequest(
    val accountNos: List<String> = emptyList()
)