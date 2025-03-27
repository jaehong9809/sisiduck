package com.a702.finafan.domain.savings.repository

import com.a702.finafan.domain.savings.model.Star

interface SavingRepository {
    suspend fun getStars(): List<Star>
}