package com.a702.finafan.presentation.ble

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.UUID

@Composable
fun UuidListScreen(viewModel: BleScanViewModel = hiltViewModel()) {
    val uuids by viewModel.nearbyUuids.collectAsState()

}


@Composable
fun UuidListContent(uuids: List<UUID>) {
    LazyColumn {
        items(uuids) { uuid ->
            Text(text = uuid.toString())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UuidListPreview() {
    val dummy = listOf(
        UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
        UUID.fromString("987e6543-e21b-32f3-a456-426614174111")
    )
    UuidListContent(dummy)
}
