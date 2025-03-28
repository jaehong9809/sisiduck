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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.theme.MainBlackWithTransparency
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Shadow.innerShadow
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 적금 거래 내역 화면
@Composable
fun SavingMainScreen(
    viewModel: SavingViewModel = viewModel(),
    savingAccountId: Long? = null
) {

    val accountInfo = SavingAccount(
        savingAccountId = 30,
        accountNo = "123-456-789000",
        accountName = "이찬원 적금",
        amount = 40000,
        interestRate = "1.80%",
        duration = 30,
        createdDt = "2025-03-01",
        imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg",
        connectAccount =
            Account(
                accountId = 1234,
                accountNo = "456-789-1000",
                bank = Bank(bankId = 12, bankCode = "345", bankName = "NH농협")
            )
    )

//    val transactions = mutableListOf<Transaction>()
    val transactions = mutableListOf(
        Transaction(
            amount = 40000,
            balance = 10000,
            message = "이찬원 사랑해",
            date = "2025-03-14",
            imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg"
        ),
        Transaction(
            amount = 40000,
            balance = 10000,
            message = "오늘 너무 귀여워",
            date = "2025-03-14",
            imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg"
        ),
        Transaction(
            amount = 40000,
            balance = 10000,
            message = "진짜 귀엽다",
            date = "2025-03-14",
            imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg"
        ),
    )

    // TODO: 적금 계좌 정보 조회 API 호출

    // TODO: 적금 입금 내역 조회 API 호출

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CommonBackTopBar(modifier = Modifier)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally
        ) {
            item {
                SavingHeader(accountInfo)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            if (transactions.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(text = stringResource(R.string.saving_item_empty_transaction),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = MainTextGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }
            } else {
                items(transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}

@Composable
fun SavingHeader(info: SavingAccount) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(info.imageUrl)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(560.dp)
            .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
    ) {

        Image(
            painter = painter,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .height(560.dp)
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
            text = "DAY ${info.duration}",
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
                text = info.accountName,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MainWhite
            )
            Text(
                text = StringUtil.formatCurrency(info.amount),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MainWhite
            )
            Text(
                text = info.accountNo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MainWhite
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSavingScreen() {
    SavingMainScreen()
}

