package com.a702.finafan.domain.ble.repository

import android.provider.ContactsContract.Data
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.ble.model.Fan
import com.a702.finafan.domain.ble.model.FanDeposit
import java.util.UUID

interface BleRepository {
    suspend fun detectNearbyUser(uuid: UUID): List<UUID>
    suspend fun registerUuid(uuid: String) : DataResource<Unit>
    suspend fun matchFans(uuids: List<String>) : DataResource<List<Fan>>
    suspend fun getMatchedFanDeposits() : DataResource<List<FanDeposit>>
}