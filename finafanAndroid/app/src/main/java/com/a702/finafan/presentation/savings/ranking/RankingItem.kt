package com.a702.finafan.presentation.savings.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.GradSubButton
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.Ranking

@Composable
fun RankingItem(
    ranking: Ranking,
    onSelect: (Ranking) -> Unit
) {

    if (ranking.rank == 1) {
        FirstStar(ranking, onSelect)
    } else {
        RestStar(ranking, onSelect)
    }
}

@Composable
fun FirstStar(
    ranking: Ranking,
    onSelect: (Ranking) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MainWhite)
    ) {

        Row(
            modifier = Modifier
                .padding(top = 24.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = ranking.starImageUrl,
                contentDescription = "Image",
                modifier = Modifier
                    .width(110.dp)
                    .height(160.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(MainBgLightGray),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 26.dp)
            ) {
                Text(
                    text = stringResource(R.string.ranking_first_title),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = MainBlack
                )

                // 스타 이름
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = ranking.starName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    color = MainBlack
                )

                // 총 금액
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = StringUtil.formatCurrency(ranking.totalAmount),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 30.sp,
                    color = MainBlack
                )

                GradSubButton(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(R.string.ranking_history),
                    fontSize = 16.sp,
                    onButtonClick = {
                        onSelect(ranking)
                    })
            }
        }
    }


}

@Composable
fun RestStar(
    ranking: Ranking,
    onSelect: (Ranking) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onSelect(ranking)
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 순위
        Text(
            modifier = Modifier,
            text = ranking.rank.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 30.sp,
            color = MainBlack
        )

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(start = 26.dp)
        ) {

            // 스타 이름
            Text(
                modifier = Modifier,
                text = ranking.starName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 30.sp,
                color = MainBlack
            )

            // 총 금액
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = StringUtil.formatCurrency(ranking.totalAmount),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                color = MainBlack
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.arrow_right_gray),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ItemPreView() {
    val ranking = Ranking(rank = 2, starId = 1, starName = "이찬원", starImageUrl = "", 2)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MainWhite)
    ) {
        FirstStar(ranking, onSelect = {})
        RestStar(ranking, onSelect = {})
    }
}