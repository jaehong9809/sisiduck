package com.a702.finafan.common.utils

import com.a702.finafan.common.domain.DataResource
import retrofit2.HttpException
import java.io.IOException


suspend fun <T> safeApiCall(
    block: suspend () -> T
): DataResource<T> {
    return try {
        DataResource.success(block())
    } catch (e: IOException) {
        DataResource.error(Throwable("인터넷 연결을 확인해주세요"))
    } catch (e: HttpException) {
        DataResource.error(Throwable("서버 오류: ${e.code()}"))
    } catch (e: Exception) {
        DataResource.error(Throwable("알 수 없는 오류"))
    }
}
