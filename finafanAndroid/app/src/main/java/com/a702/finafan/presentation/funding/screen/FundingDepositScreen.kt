package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonTextField
import com.a702.finafan.common.ui.component.LiveTextArea
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.component.SelectAccountField
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.Star
import com.a702.finafan.presentation.funding.component.FundingInfoHeader
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel
import java.time.LocalDate

@Composable
fun FundingDepositScreen(
    funding: Funding
) {
    val savingViewModel: SavingViewModel = hiltViewModel()

    val savingState by savingViewModel.savingState.collectAsState()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(funding.star.thumbnail)
            .build()
    )

    val amount = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainWhite)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // 상단 바
        CommonBackTopBar(text = stringResource(R.string.funding_deposit_title))

        Spacer(modifier = Modifier.height(24.dp))

        FundingInfoHeader(funding)

        Spacer(modifier = Modifier.height(24.dp))

        Text("출금 계좌 선택", fontWeight = FontWeight.Medium, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        SelectAccountField(savingViewModel, savingState.withdrawalAccounts)

        Spacer(modifier = Modifier.height(24.dp))

        Text("금액", fontWeight = FontWeight.Medium, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        CommonTextField(
            hint = "금액 입력",
            text = amount,
            isMoney = true
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text("메시지", fontWeight = FontWeight.Medium, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        LiveTextArea(
            placeholder = "메시지 입력 (선택)",
            description = message,
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(32.dp))

        PrimaryGradBottomButton(
            onClick = {},
            text = "입금하기"
        )
    }
}

@Preview
@Composable
fun FundingDepositScreenPreview() {
    val fakeNavController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides fakeNavController) {
        FundingDepositScreen(
            Funding(
            star = Star(
                id = 1L,
                name = "이찬원",
                index = 0,
                image = "https://example.com/image1.jpg",
                thumbnail = "https://example.com/thumb1.jpg"
            ),
            id = 101L,
            title = "뮤비 촬영장 커피차 서포트",
            accountNo = "312-0139-3754-31",
            status = "ONGOING",
            currentAmount = 82000L,
            goalAmount = 200000L,
            fundingExpiryDate = LocalDate.now().plusDays(7)
        ))
    }
}