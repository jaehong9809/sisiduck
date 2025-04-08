package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.Shadow.innerShadow

@Composable
fun ThreeTabRow(
    labels: List<String>,
    containerColor: Color,
    selectedTabColor: Color,
    onTabSelected: List<() -> Unit>, // 호출할 API 메서드 리스트
    selectedIndex: MutableIntState = rememberSaveable { mutableIntStateOf(0) },
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier
        .fillMaxWidth()
        ) {
        TabRow(
            selectedTabIndex = selectedIndex.intValue,
            containerColor = containerColor,
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)) ,
            indicator = {},
            divider = {}
        ) {
            labels.forEachIndexed { index, label ->
                Tab(
                    selected = selectedIndex.intValue == index,
                    onClick = {
                        selectedIndex.intValue = index
                        onTabSelected[index].invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (selectedIndex.intValue == index) selectedTabColor else Color.Transparent,
                            shape = RoundedCornerShape(15.dp)
                        ).then(
                            if (index == selectedIndex.intValue) Modifier.innerShadow(
                                shape = RoundedCornerShape(15.dp),
                                color = Color.Black.copy(alpha = 0.25f),
                                blur = 5.dp,
                                offsetX = 2.dp,
                                offsetY = 2.dp,
                                spread = 3.dp
                            ) else Modifier
                        )
,
                    text = {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            fontFamily = Pretendard,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (selectedIndex.intValue == index) Color.White else Color.Black
                        )
                    }
                )
            }
        }
    }
}