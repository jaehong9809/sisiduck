package com.a702.finafan.infrastructure.util

import android.content.Context
import java.util.UUID

object BleUuidUtil {

    // TODO: 보안 측면, 리팩토링 시 암호화 적용
    fun getRotatingUuid(context: Context): UUID {
        return UUID.randomUUID()
    }
}