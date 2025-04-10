package com.a702.finafan.presentation.ble

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.ble.model.Fan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BleFanRadarViewModel @Inject constructor(

) : ViewModel() {

    // 프로필 URL 상태. 실제로는 네트워크 또는 로컬 데이터로부터 가져오는 값으로 업데이트
    private val _myProfileUrl = MutableStateFlow("https://example.com/default_profile.jpg")
    val myProfileUrl: StateFlow<String> = _myProfileUrl

    // 팬 목록 상태. 실제 데이터로 업데이트 가능함.
    private val _fans = MutableStateFlow<List<Fan>>(emptyList())
    val fans: StateFlow<List<Fan>> = _fans

    init {
        // 초기 데이터 로드를 위한 예시. 실제 로직에서는 repository 호출 등 비동기 작업 수행.
        viewModelScope.launch {
            // 예시 지연. 실제 네트워크 호출이나 DB 로드 작업으로 대체할 수 있음.
            delay(1000)

            // 팬 목록 예시 데이터
            _fans.value = listOf(
                //Fan(id = "1", name = "Fan One", profileUrl = "https://example.com/fan1.jpg"),
                //Fan(id = "2", name = "Fan Two", profileUrl = "https://example.com/fan2.jpg")
            )

            // myProfileUrl 또한 필요시 업데이트할 수 있음.
            _myProfileUrl.value = "https://example.com/my_profile.jpg"
        }
    }
}