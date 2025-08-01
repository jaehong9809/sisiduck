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
import androidx.compose.ui.draw.shadow
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
import com.a702.finafan.common.ui.theme.MainBlackTransparency
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.account.model.Account
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel
import com.a702.finafan.presentation.funding.viewmodel.FundingViewModel
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 전체 계좌 목록 화면
@Composable
fun AllAccountScreen(
    selectedTabIndex: MutableIntState,
    savingViewModel: SavingViewModel = viewModel(),
    fundingViewModel: FundingViewModel = viewModel(),
    accountViewModel: AccountViewModel = viewModel()
) {

    val navController = LocalNavController.current
    val context = LocalContext.current

    val savingState by savingViewModel.savingState.collectAsState()
    val fundingState by fundingViewModel.fundingState.collectAsState()
    val accountState by accountViewModel.accountState.collectAsState()

    val isLoggedIn by savingViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(Unit) {
        when (selectedTabIndex.intValue) {
            0 -> savingViewModel.fetchSavingAccount()
            1 -> fundingViewModel.fetchFundings(FundingFilter.MY)
            2 -> accountViewModel.fetchWithdrawalAccount()
        }
    }

    val count by remember(selectedTabIndex, savingState, fundingState, accountState) {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> savingState.savingAccountInfo.accounts.size
                1 -> fundingState.fundings.size
                2 -> accountState.withdrawalAccounts.size
                else -> 0
            }
        }
    }

    val accountAmount by remember(selectedTabIndex, savingState, fundingState, accountState) {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> savingState.savingAccountInfo.total
                1 -> fundingState.totalAmount
                else -> 0
            }
        }
    }

    val accountList by remember(selectedTabIndex, savingState, fundingState, accountState) {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> savingState.savingAccountInfo.accounts
                1 -> fundingState.fundings
                2 -> accountState.withdrawalAccounts
                else -> emptyList()
            }
        }
    }

    val isLoading by remember(selectedTabIndex, savingState, fundingState, accountState) {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> savingState.isLoading
                1 -> fundingState.isLoading
                2 -> accountState.isLoading
                else -> true
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
                            savingViewModel.fetchSavingAccount()
                        },
                        {
                            selectedTabIndex.intValue = 1
                            fundingViewModel.fetchFundings(FundingFilter.MY)
                        },
                        {
                            selectedTabIndex.intValue = 2
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
                            1 -> navController.navigate(NavRoutes.FundingCreate.route)
                            2 -> {
                                if (isLoggedIn) {
                                    navController.navigate(NavRoutes.ConnectBank.from("allAccount"))
                                } else {
                                    navController.navigate("login")
                                }
                            }
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
                    isLoading -> {
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
                                1 -> {
                                    val funding = account as Funding
                                    FundingItem(
                                        funding = funding,
                                        onSelect = {
                                            navController.navigate(NavRoutes.FundingDetail.withId(funding.id))
                                        }
                                    )
                                }
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
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = MainBlackTransparency,
                    spotColor = MainBlackTransparency
                )
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
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = MainBlackTransparency,
                    spotColor = MainBlackTransparency
                )
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
                    .padding(vertical = 15.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
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

@Composable
fun FundingItem(
    funding: Funding,
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
                    onClick = { onSelect() }
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = funding.title,
                        color = MainBlack,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 24.sp,
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "종료일: ${funding.fundingExpiryDate}",
                        color = MainTextGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = StringUtil.formatCurrency(funding.currentAmount),
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
