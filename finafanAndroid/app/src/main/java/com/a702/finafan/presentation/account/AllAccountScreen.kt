package com.a702.finafan.presentation.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.BlackButton
import com.a702.finafan.common.ui.component.CommonProgress
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.AccountBoxGray
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.account.model.Account
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 전체 계좌 목록 화면
@Composable
fun AllAccountScreen(
    selectedTabIndex: MutableIntState,
    savingViewModel: SavingViewModel = viewModel(),
    accountViewModel: AccountViewModel = viewModel()
) {

    val navController = LocalNavController.current
    val context = LocalContext.current

    val savingState by savingViewModel.savingState.collectAsState()
    val accountState by accountViewModel.accountState.collectAsState()

    LaunchedEffect(Unit) {
        when (selectedTabIndex.intValue) {
            0 -> savingViewModel.fetchSavingAccount()
            1 -> 0 // TODO: 모금 통장 API
            2 -> accountViewModel.fetchWithdrawalAccount()
        }
    }

    val count by remember(selectedTabIndex, accountState) {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> savingState.savingAccountInfo.accounts.size
                1 -> 0 // TODO: 모금 통장 개수
                2 -> accountState.withdrawalAccounts.size
                else -> 0
            }
        }
    }

    val accountAmount by remember(selectedTabIndex, accountState) {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> savingState.savingAccountInfo.total
                1 -> 0 // TODO: 모금 통장 총액
                else -> 0
            }
        }
    }

    val accountList by remember(selectedTabIndex, accountState) {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> savingState.savingAccountInfo.accounts
                1 -> emptyList() // TODO: 모금 통장 리스트
                2 -> accountState.withdrawalAccounts
                else -> emptyList()
            }
        }
    }

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        ConfirmDialog(
            showDialog,
            content = context.getString(R.string.saving_item_limit),
            onClickConfirm = {
                showDialog.value = false
            }
        )
    }

    val buttonText by remember(selectedTabIndex) {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> context.getString(R.string.all_account_create_saving)
                1 -> context.getString(R.string.all_account_create_funding)
                else -> context.getString(R.string.all_account_connect_bank)
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MainBgLightGray)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 12.dp),
                    text = stringResource(R.string.all_account_title),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainBlack,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )

                ThreeTabRow(
                    modifier = Modifier.padding(vertical = 12.dp),
                    labels = listOf(
                        stringResource(R.string.all_account_saving_title),
                        stringResource(R.string.all_account_funding_title),
                        stringResource(R.string.all_account_other_title)
                    ),
                    containerColor = MainWhite,
                    selectedTabColor = MainBlack,
                    selectedIndex = selectedTabIndex,
                    onTabSelected = listOf(
                        {
                            selectedTabIndex.intValue = 0
                            // 적금 계좌 목록
                            savingViewModel.fetchSavingAccount()
                        },
                        {
                            selectedTabIndex.intValue = 1
                            // TODO: 모금 계좌 목록 API 호출
                        },
                        {
                            selectedTabIndex.intValue = 2
                            // 출금 계좌 목록
                            accountViewModel.fetchWithdrawalAccount()
                        }
                    ),
                )

                AllAccountHeader(selectedTabIndex, count, accountAmount)

                Spacer(modifier = Modifier.height(20.dp))
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(MainBgLightGray)
                    .padding(16.dp)
            ) {
                BlackButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        when (selectedTabIndex.intValue) {
                            0 -> {
                                if (savingState.savingAccountInfo.accounts.size == 3) {
                                    showDialog.value = true
                                } else {
                                    navController.navigate(NavRoutes.SavingDesc.route)
                                }
                            }
                            1 -> "" // TODO: 모금통장 가입 화면으로 이동
                            2 -> navController.navigate(NavRoutes.Account.route)
                        }
                    },
                    text = buttonText.toString(),
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MainBgLightGray)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                when {
                    accountState.isLoading -> {
                        item { CommonProgress() }
                    }
                    accountList.isEmpty() -> {
                        item {
                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.all_account_list_empty),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = MainTextGray,
                                textAlign = TextAlign.Center,
                                lineHeight = 30.sp
                            )
                        }
                    }
                    else -> {
                        items(accountList) { account ->
                            when (selectedTabIndex.intValue) {
                                0 -> SavingAccountItem(
                                    account as SavingAccount,
                                    onSelect = {
                                        navController.navigate(NavRoutes.SavingMain.route + "/${account.accountId}")
                                    }
                                )
                                1 -> SavingAccountItem(
                                    account as SavingAccount,
                                    onSelect = {
                                        // TODO: 모금통장 타입으로 변경 필요, 모금 통장 상세 화면으로 이동
                                    }
                                )
                                2 -> WithdrawalAccountItem(
                                    account as Account,
                                    onSelect = {
                                        accountViewModel.updateConnectAccount(account)
                                        navController.navigate(NavRoutes.ConnectAccount.route)
                                    }
                                )
                            }
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun AllAccountHeader(
    selectedTabIndex: MutableIntState,
    count: Int,
    accountAmount: Long
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AccountBoxGray, shape = RoundedCornerShape(15.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 18.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 개수
            Row(
                modifier = Modifier.wrapContentSize()
            ) {
                Text(
                    text = stringResource(R.string.all_account_count_header),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainBlack,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = count.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainBlack,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )


                Text(
                    text = stringResource(R.string.all_account_count_content),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainBlack,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // 계좌 총 잔액
            if (selectedTabIndex.intValue < 2) {
                Text(
                    text = StringUtil.formatCurrency(accountAmount),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainBlack,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun WithdrawalAccountItem(account: Account, onSelect: () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MainWhite, RoundedCornerShape(20.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onSelect()
                    }
                ),
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 26.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(id = BankEnum.getDrawable(account.bank.bankCode)),
                    contentDescription = "info",
                )

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = account.bank.bankName + " " + account.accountNo,
                    color = MainBlack,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SavingAccountItem(account: SavingAccount, onSelect: () -> Unit) {
    CommonAccountItem(
        accountName = account.accountName,
        accountNo = account.accountNo,
        amount = account.amount,
        onSelect = onSelect
    )
}

@Composable
fun CommonAccountItem(
    accountName: String,
    accountNo: String,
    amount: Long,
    onSelect: () -> Unit
) {

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MainWhite, RoundedCornerShape(20.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onSelect()
                    }
                ),
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 26.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.saving_item_name, accountName),
                        color = MainBlack,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 24.sp,
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = stringResource(R.string.all_account_saving_account_no, accountNo),
                        color = MainTextGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = StringUtil.formatCurrency(amount),
                    color = MainBlack,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun AllAccountPreview() {
    SavingAccountItem(SavingAccount(), onSelect = {})

//    WithdrawalAccountItem(
//        account = Account(
//            accountId = 1234,
//            accountNo = "456-789-1000",
//            bank = Bank(bankId = 12, bankCode = "345", bankName = "NH농협")
//        )
//    )

//    AllAccountScreen()
}