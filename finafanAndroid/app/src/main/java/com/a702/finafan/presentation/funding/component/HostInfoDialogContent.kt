package com.a702.finafan.presentation.funding.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.a702.finafan.data.funding.dto.response.AdminUser

@Composable
fun HostInfoDialogContent(
    hostInfo: AdminUser?,
    description: String?
) {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(10.dp)) {
        // 진행자 정보 제목
        MenuTitle(
            content = "진행자 정보",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "User",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                val maskedName = hostInfo?.adminName?.let {
                    if (it.length >= 2) it.replaceRange(1, it.length - 1, "*") else it
                } ?: ""

                Text(
                    text = maskedName,
                    style = MaterialTheme.typography.bodyMedium
                )

                if ((hostInfo?.fundingSuccessCount ?: 0) >= 5) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✅")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "5회 이상 모금 진행 성공",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        MenuTitle(
            content = "모금 상세",
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = description ?: "",
            style = MaterialTheme.typography.bodySmall
        )
    }
}