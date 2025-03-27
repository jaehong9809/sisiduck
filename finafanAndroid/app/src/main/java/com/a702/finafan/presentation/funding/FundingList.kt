package com.a702.finafan.presentation.funding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun FundingList(
    fundings: List<Funding>,
    stars: List<Star>,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(fundings) { funding ->
            val star: Star = stars[funding.starIndex]
            FundingCardItem(star, funding, navController)
        }
    }
}
