package com.a702.finafan.presentation.ble

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UuidListScreen(viewModel: BleScanViewModel = hiltViewModel()) {
    val uuids by viewModel.nearbyUuids.collectAsState()

    LazyColumn {
        items(uuids) { uuid ->
            Text(text = uuid.toString())
        }
    }
}
