package com.a702.finafan.presentation.ble.dummy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.theme.MainBgLightGray

data class FanDepositDummy(
    val name: String,
    val message: String,
    val amount: String,
    val imageUrl: String
)

val url = "https://specialized702.s3.ap-northeast-2.amazonaws.com/herocontextPNG.PNG"

val dummyFanDeposits = listOf(
    FanDepositDummy("임영웅", "영웅시대가 응원해!", "+ 50,000원", url),
    FanDepositDummy("임영웅", "콘서트 최고\uD83D\uDC96", "+ 30,000원", url),
)

@Composable
fun MatchedFanDepositScreenDummy() {
    Scaffold(
        topBar = {
            CommonBackTopBar(text = "주변 팬 보기", isTextCentered = true)
        },
        containerColor = MainBgLightGray
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {

            Spacer(Modifier.height(24.dp))

            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "이재홍의 응원 내역",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "100,000원",
                        color = Color.Gray,
                        fontSize = 22.sp
                    )
                }

                TextButton(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.textButtonColors(containerColor = Color(0xFFE8EBF2)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("나의 응원 내역", fontSize = 18.sp, color = Color.DarkGray)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Support Cards
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                dummyFanDeposits.forEach { fan ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 왼쪽: 팬 정보
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = fan.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(52.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(Modifier.width(16.dp))

                                Column {
                                    Text(
                                        fan.name,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        fan.message,
                                        fontSize = 16.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            // 오른쪽: 금액 (고정 위치)
                            Text(
                                text = fan.amount,
                                color = Color(0xFF2D6FF7),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}
