package com.a702.finafan.presentation.ble

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.a702.finafan.common.ui.theme.CustomTypography.bodyMedium
import com.a702.finafan.common.ui.theme.CustomTypography.displayMedium
import com.a702.finafan.common.ui.theme.MainBgLightGray
import java.util.UUID

@Composable
fun UuidListScreen(viewModel: BleScanViewModel = hiltViewModel()) {
    val uuids by viewModel.nearbyUuids.collectAsState()
    UuidListContent(uuids)
}

@Composable
fun UuidListContent(uuids: List<UUID>) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "주변에서 감지된 UUID",
            style = displayMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (uuids.isEmpty()) {
            Text(text = "아직 감지된 UUID가 없습니다.")
        } else {
            LazyColumn {
                items(uuids) { uuid ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MainBgLightGray
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = uuid.toString(),
                            style = bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UuidListContentPreview() {
    val dummys = listOf(
        UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
        UUID.fromString("987e6543-e21b-32f3-a456-426614174111"),
        UUID.fromString("456e7890-a45c-78d9-b123-123456789abc")
    )
    UuidListContent(dummys)
}

