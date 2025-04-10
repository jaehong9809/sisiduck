package com.a702.finafan.data.ble.repository

import com.a702.finafan.common.data.dto.getOrThrow
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.utils.safeApiCall
import com.a702.finafan.data.ble.api.BleApi
import com.a702.finafan.data.ble.datasource.BleRemoteDataSource
import com.a702.finafan.data.ble.dto.request.MatchFansUuidRequest
import com.a702.finafan.data.ble.dto.request.RegisterBleUuidRequest
import com.a702.finafan.data.ble.dto.response.toDomain
import com.a702.finafan.domain.ble.model.Fan
import com.a702.finafan.domain.ble.model.FanDeposit
import com.a702.finafan.domain.ble.repository.BleRepository
import java.util.UUID
import javax.inject.Inject

class BleRepositoryImpl @Inject constructor(
    private val api: BleRemoteDataSource,
    private val bleApi: BleApi
) : BleRepository {
    override suspend fun detectNearbyUser(uuid: UUID): List<UUID> {
        return api.sendDetectedUuid(uuid)
    }

    override suspend fun registerUuid(uuid: String): DataResource<Unit> = safeApiCall {
        bleApi.registerUuid(RegisterBleUuidRequest(uuid)).getOrThrow {  }
    }

    override suspend fun matchFans(uuids: List<String>): DataResource<List<Fan>> = safeApiCall {
        bleApi.matchFans(MatchFansUuidRequest(uuids)).getOrThrow { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getMatchedFanDeposits(): DataResource<List<FanDeposit>> = safeApiCall {
        bleApi.getMatchedFanDeposits().getOrThrow { list ->
            list.map { it.toDomain() }
        }
    }

}