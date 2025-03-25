package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun ThreeTabRow(
    labels: List<String>,
    containerColor: Color,
    selectedTabColor: Color,
    onTabSelected: List<() -> Unit>, // 호출할 API 메서드 리스트
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(modifier = modifier
        .fillMaxWidth()
        .background(color = MainWhite, shape = RoundedCornerShape(15.dp))
        ) {
        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = containerColor,
            modifier = Modifier.fillMaxWidth(),
            indicator = {},
            divider = {}
        ) {
            labels.forEachIndexed { index, label ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        onTabSelected[index].invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (selectedIndex == index) selectedTabColor else Color.Transparent,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    text = {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (selectedIndex == index) Color.White else Color.Black
                        )
                    }
                )
            }
        }
    }
}

// 여기부터는 테스트 코드입니다
@Preview
@Composable
fun FundingScreen() {
    ThreeTabRow(
        labels = listOf("진행 중 모금", "참여 중 모금", "내가 만든 모금"),
        containerColor = MainWhite,
        selectedTabColor = Color.Blue,
        onTabSelected = listOf(
            { getAllFundings() },
            { getParticipatingFundings() },
            { getMyFundings() }
        )
    )
}

fun getAllFundings() {
    println("모든 모금 조회 API 호출")
}

fun getParticipatingFundings() {
    println("참여 중인 모금 조회 API 호출")
}

fun getMyFundings() {
    println("내가 생성한 모금 조회 API 호출")
}