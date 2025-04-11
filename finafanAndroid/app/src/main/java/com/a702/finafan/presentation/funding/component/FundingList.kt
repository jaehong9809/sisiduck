package com.a702.finafan.presentation.funding.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.Star

@Composable
fun FundingList(
    fundings: List<Funding>,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(fundings) { funding ->
            val star: Star = funding.star
            FundingCardItem(star, funding, navController)
        }
    }
}
