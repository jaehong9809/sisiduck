package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonTextField
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.component.CustomGradBottomButton
import com.a702.finafan.common.ui.component.LiveTextArea
import com.a702.finafan.common.ui.component.SelectAccountField
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil.formatNumber
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel
import com.a702.finafan.presentation.funding.component.FundingInfoHeader
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun FundingDepositScreen(
    navController: NavController,
    fundingDetailViewModel: FundingDetailViewModel,
    accountViewModel: AccountViewModel
) {
    val accountState by accountViewModel.accountState.collectAsState()
    val fundingState by fundingDetailViewModel.uiState.collectAsState()

    val amount = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        accountViewModel.fetchWithdrawalAccount()
    }

    Scaffold(
        containerColor = MainWhite,
        topBar = {
            CommonBackTopBar(text = stringResource(R.string.funding_deposit_title))
        },
        bottomBar = {
            CustomGradBottomButton(
                gradientColor = fundingState.colorSet,
                onClick = {
                    fundingState.funding?.let {
                        fundingDetailViewModel.createDeposit(
                            it.id,
                            Deposit(
                                accountId = accountState.selectAccount.accountId,
                                balance = formatNumber(amount.value),
                                name = "로그인해서 받아와야...", // TODO: 로그인 값 받아와야...
                                message = message.value
                            )
                        )
                        showDialog.value = true
                    }
                },
                text = stringResource(R.string.btn_deposit),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                fundingState.funding?.let {
                    FundingInfoHeader(it)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("출금 계좌 선택", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                SelectAccountField(accountViewModel, accountState.withdrawalAccounts)

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
                    placeholder = stringResource(R.string.funding_deposit_message),
                    description = message,
                    onValueChange = {}
                )

                Spacer(modifier = Modifier.height(32.dp))
            }

            if (showDialog.value) {
                ConfirmDialog(
                    showDialog = showDialog,
                    content = "입금이 완료되었습니다!",
                    onClickConfirm = {
                        showDialog.value = false
                        fundingState.funding?.let { funding ->
                            navController.navigate(NavRoutes.FundingDetail.withId(funding.id)) {
                                popUpTo(NavRoutes.FundingDeposit.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }
}
