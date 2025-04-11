package com.a702.finafan.presentation.ble.dummy

import androidx.compose.runtime.Composable
import com.a702.finafan.domain.ble.model.Fan
import com.a702.finafan.presentation.ble.FanRadarScreen

@Composable
fun BleFanRadarScreenDummy(
    onShowCheerClick: () -> Unit = {}
) {
    val heroUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/775e6e08-eb42-4ef7-82b7-84fc5465afb0_1744257521339.jpg"

    val dummyFans = listOf(
        Fan(id = 1, name = "이*호", profileUrl = heroUrl),
        Fan(id = 2, name = "강*주", profileUrl = heroUrl)
    )

    FanRadarScreen(
        myProfileUrl = heroUrl,
        fans = dummyFans,
        onShowCheerClick = onShowCheerClick
    )
}
