package com.a702.finafan.domain.savings.model

data class Ranking(
    val star: Star = Star(),
    val rankingIdx: Int = 1,
    val amount: Long = 0
)
