package com.a702.finafan.data.savings.repository

import android.util.Log
import com.a702.finafan.data.savings.api.SavingApi
import com.a702.finafan.data.savings.dto.response.toDomain
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class SavingRepositoryImpl @Inject constructor(
    private val api: SavingApi
): SavingRepository {

    override suspend fun getStars(): List<Star> {
        val response = api.getStars()

        Log.d("API Response", "Response Body: ${response.data}")

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

}