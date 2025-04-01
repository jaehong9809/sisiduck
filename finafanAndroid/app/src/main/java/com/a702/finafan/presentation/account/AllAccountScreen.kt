package com.a702.finafan.presentation.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.E3E6EA
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 전체 계좌 목록 화면
@Composable
fun AllAccountScreen(
    savingViewModel: SavingViewModel = viewModel()
) {

    val savingState by savingViewModel.savingState.collectAsState()
    var selectedTabIndex = remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(MainBgLightGray)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                            savingViewModel.fetchWithdrawalAccount()
                        }
                    ),
                )

                AllAccountHeader()

                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        val accountList = when (selectedTabIndex.intValue) {
            0 -> savingState.savingAccounts
            1 -> emptyList() // TODO: 모금 통장 리스트
            2 -> savingState.withdrawalAccounts
            else -> emptyList()
        }

        if (accountList.isNotEmpty()) {
            items(accountList) { account ->
                when (selectedTabIndex.intValue) {
                    0 -> AccountItem(account as SavingAccount)
                    1 -> AccountItem(account as SavingAccount) // TODO: 모금통장 타입으로 변경 필요
                    2 -> WithdrawalAccountItem(account as Account)
                }
            }
        }
    }


}

@Composable
fun AllAccountHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(E3E6EA, shape = RoundedCornerShape(15.dp))
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
                    text = "8",
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
            Text(
                text = StringUtil.formatCurrency(1000000),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MainBlack,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun WithdrawalAccountItem(account: Account) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MainWhite, RoundedCornerShape(20.dp)),
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 26.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: 은행명에 해당하는 은행 사진
                Image(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(id = R.drawable.info_circle),
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
fun AccountItem(account: SavingAccount) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MainWhite, RoundedCornerShape(20.dp)),
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
                        text = stringResource(R.string.saving_item_name, account.accountName),
                        color = MainBlack,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 24.sp,
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = stringResource(R.string.all_account_saving_account_no, account.accountNo),
                        color = MainTextGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = StringUtil.formatCurrency(account.amount),
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
    AccountItem(SavingAccount())

//    WithdrawalAccountItem(
//        account = Account(
//            accountId = 1234,
//            accountNo = "456-789-1000",
//            bank = Bank(bankId = 12, bankCode = "345", bankName = "NH농협")
//        )
//    )

//    AllAccountScreen()
}