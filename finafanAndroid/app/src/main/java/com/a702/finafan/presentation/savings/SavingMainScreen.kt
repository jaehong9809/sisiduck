package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonProgress
import com.a702.finafan.common.ui.theme.MainBlackWithTransparency
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Shadow.innerShadow
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.SavingAccount
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
                    SavingHeader(savingState.savingInfo.savingAccount)
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                when {
                    savingState.isLoading -> {
                        item { CommonProgress() }
                    }
                    savingState.savingInfo.transactions.isEmpty() -> {
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
                        items(savingState.savingInfo.transactions) { transaction ->
                            TransactionItem(transaction, onSelect = {
                                viewModel.setTransaction(transaction)
                                navController.navigate(NavRoutes.TransactionDetail.route)
                            })
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun SavingHeader(account: SavingAccount) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(account.imageUrl)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(530.dp)
            .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
    ) {

        Image(
            painter = painter,
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
            color = MainWhite,
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
                color = MainWhite
            )
            Text(
                text = StringUtil.formatCurrency(account.amount),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MainWhite
            )
            Text(
                text = account.accountNo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MainWhite,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSavingScreen() {
    SavingMainScreen(savingAccountId = 0)
}

