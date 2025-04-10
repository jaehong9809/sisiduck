package com.a702.finafan.presentation.savings

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonProgress
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainBlackWithTransparency
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Shadow.innerShadow
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 적금 거래 내역 화면
@Composable
fun SavingMainScreen(
    viewModel: SavingViewModel = viewModel(),
    savingAccountId: Long
) {

    val navController = LocalNavController.current
    val savingState by viewModel.savingState.collectAsState()

    // 적금 계좌 정보, 입금 내역 목록
    LaunchedEffect(Unit) {
        viewModel.fetchSavingInfo(savingAccountId)
    }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                modifier = Modifier,
                text = stringResource(R.string.saving_account_manage_title),
                textOnClick = {
                    navController.navigate(NavRoutes.SavingAccountManage.route)
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .background(MainWhite)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = CenterHorizontally
            ) {
                item {
                    SavingHeader(savingState.savingAccount)
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                when {
                    savingState.isLoading -> {
                        item { CommonProgress() }
                    }
                    savingState.transactions.isEmpty() -> {
                        item {
                            Spacer(modifier = Modifier.height(18.dp))

                            Text(text = stringResource(R.string.saving_item_empty_transaction),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = MainTextGray,
                                textAlign = TextAlign.Center,
                                lineHeight = 30.sp
                            )
                        }
                    }
                    else -> {
                        item {
                            SavingHistoryList(navController, viewModel, savingState.transactions)
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun SavingHeader(account: SavingAccount) {

    val context = LocalContext.current
    val imageUrl = account.imageUrl

    var textColor by remember { mutableStateOf(MainWhite) }

    // 이미지 색상 분석
    LaunchedEffect(imageUrl) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = loader.execute(request)
        val bitmap = (result.drawable as? BitmapDrawable)?.bitmap

        bitmap?.let {
            Palette.from(it).generate { palette ->
                val dominant = palette?.getDominantColor(Color.White.toArgb()) ?: Color.White.toArgb()
                val luminance = ColorUtils.calculateLuminance(dominant)
                textColor = if (luminance < 0.5) MainWhite else MainBlack
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(530.dp)
            .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
    ) {

        AsyncImage(
            model = account.imageUrl,
            contentDescription = "Star Image",
            modifier = Modifier
                .fillMaxSize()
                .height(530.dp)
                .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
                .background(LightGray)
                .align(Alignment.Center)
                .innerShadow(
                    shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp),
                    color = MainBlackWithTransparency,
                    blur = 100.dp,
                    offsetX = 0.dp,
                    offsetY = (-10).dp,
                    spread = 0.dp
                ),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "DAY ${account.duration}",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .padding(28.dp)
                .align(BottomCenter),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.saving_item_name, account.accountName),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                color = textColor
            )
            Text(
                text = StringUtil.formatCurrency(account.amount),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                color = textColor
            )
            Text(
                text = account.accountNo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
                lineHeight = 40.sp,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}

@Composable
fun SavingHistoryList(
    navController: NavController,
    viewModel: SavingViewModel,
    transactions: List<Transaction>
) {
    val groupedTransactions = transactions
        .sortedByDescending { it.createdAt }
        .groupBy { it.createdAt.year }
        .mapValues { (_, yearGroup) ->
            yearGroup.groupBy { it.createdAt.toLocalDate() }
        }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        groupedTransactions.forEach { (year, dateMap) ->
            Text(
                text = "${year}년",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MainTextGray,
                modifier = Modifier.padding(top = 12.dp)
            )

            dateMap.toSortedMap(compareByDescending { it })
                .forEach { (date, transactions) ->
                    Text(
                        text = "${date.monthValue}월 ${date.dayOfMonth}일",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MainTextGray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    transactions.forEach { transaction ->
                        TransactionItem(transaction, onSelect = {
                            viewModel.setTransaction(transaction)
                            navController.navigate(NavRoutes.TransactionDetail.route)
                        })
                    }
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSavingScreen() {
    SavingMainScreen(savingAccountId = 0)
}

