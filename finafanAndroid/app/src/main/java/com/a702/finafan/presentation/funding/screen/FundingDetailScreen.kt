package com.a702.finafan.presentation.funding.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.ui.component.BottomSheetLayout
import com.a702.finafan.common.ui.component.Card
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CustomGradButton
import com.a702.finafan.common.ui.component.DialogLayout
import com.a702.finafan.common.ui.component.ImageItem
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.Typography
import com.a702.finafan.common.ui.theme.starThemes
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.model.FundingStatus
import com.a702.finafan.presentation.funding.component.BottomButtonByStatus
import com.a702.finafan.presentation.funding.component.DepositHistoryList
import com.a702.finafan.presentation.funding.component.FundingDetailHeader
import com.a702.finafan.presentation.funding.component.HostInfoDialogContent
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel
import com.a702.finafan.presentation.main.viewmodel.MainViewModel
import com.a702.finafan.presentation.navigation.LocalNavController

@Composable
fun FundingDetailScreen(
    fundingId: Long,
    viewModel: FundingDetailViewModel,
    mainViewModel: MainViewModel
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()
    val userState by mainViewModel.userState.collectAsState()
    val userIdState = remember { mutableStateOf<Long?>(null) }

    val showBottomSheet = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    val funding = uiState.funding
    val status = funding?.status
    val isHost = uiState.isHost
    val isParticipant = uiState.isParticipant
    val hostInfo = uiState.hostInfo
    val description = uiState.description

    val colorSet: List<Color> = listOf(
        starThemes[funding?.star?.index ?: 0].start,
        starThemes[funding?.star?.index ?: 0].mid,
        starThemes[funding?.star?.index ?: 0].end
    )


    val displayName = hostInfo?.adminName?.let { name ->
        if (name.length >= 2) {
            name.replaceRange(1, 2, "*")
        } else {
            name
        }
    } ?: ""

    LaunchedEffect(fundingId) {
        viewModel.fetchFundingDetail(fundingId)
        mainViewModel.fetchUserInfo()
        when (val state = userState) {
            is DataResource.Success -> userIdState.value = state.data.userId.toLong()
            else -> Log.d("로그인", "로그인이 필요합니다")
        }
    }

    val userId = userIdState.value

    LaunchedEffect(userId, uiState.hostInfo) {
        val uid = userId
        val host = uiState.hostInfo
        if (uid != null && host != null) {
            viewModel.updateIsHost(uid)
            Log.d("🔥 isHost 호출", "host: ${host.id}, userId: $uid")
        }
    }

    LaunchedEffect(funding) {
        viewModel.fetchDepositHistory(fundingId, DepositFilter.ALL)
        if (status == FundingStatus.CANCELED || status == FundingStatus.FAILED) {
                showBottomSheet.value = true
        }
    }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                text = "모금 보기",
                backgroundColor = Color.Transparent
            )
        },
        bottomBar = {
            BottomButtonByStatus(
                status = status,
                isHost = isHost,
                isParticipant = isParticipant,
                colorSet = colorSet,
                fundingId = fundingId,
                onNavigate = { navController.navigate(it) }
            )
        },
        containerColor = MainWhite
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MainWhite)
                .verticalScroll(rememberScrollState())
        ) {
            funding?.let {
                FundingDetailHeader(it, it.star, colorSet)
            }

            if (isParticipant) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainWhite)
                        .padding(horizontal = 14.dp)
                ) {
                    CustomGradButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 26.dp, horizontal = 6.dp),
                        onClick = {
                            showDialog.value = true
                        },
                        text = stringResource(R.string.funding_detail_title),
                        gradientColor = listOf(colorSet[0], colorSet[2])
                    )
                    MenuTitle("모금 내역", modifier = Modifier.padding(horizontal = 6.dp))
                    ThreeTabRow(
                        labels = listOf("전체", "나의 내역"),
                        containerColor = MainWhite,
                        selectedTabColor = MainBlack,
                        onTabSelected = listOf(
                            { viewModel.fetchDepositHistory(fundingId, DepositFilter.ALL) },
                            { viewModel.fetchDepositHistory(fundingId, DepositFilter.MY) }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 22.dp)
                    )
                    DepositHistoryList(uiState.deposits)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(MainBgLightGray)
                        .padding(horizontal = 20.dp)
                ) {
                    MenuTitle(stringResource(R.string.funding_detail_title), modifier = Modifier.padding(top = 26.dp, bottom = 18.dp))
                    Card(description, modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp))
                    Column {
                        Text(stringResource(R.string.funding_detail_host_title), style = Typography.displaySmall, modifier = Modifier.padding(bottom = 5.dp))
                        Row {
                            ImageItem(Modifier.padding(5.dp), {}, R.drawable.user_circle)
                            Column(modifier = Modifier.padding(5.dp)) {
                                Text(displayName, fontSize = 14.sp, fontFamily = Pretendard, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 5.dp))
                                Text(stringResource(R.string.funding_detail_host_history) + " ${hostInfo?.fundingSuccessCount}회 이상 모금 진행 성공", style = Typography.labelLarge)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }

        BottomSheetLayout(
            showBottomSheet = showBottomSheet,
            confirmBtnText = stringResource(R.string.btn_confirm),
            onClickConfirm = { showBottomSheet.value = false }
        ) {
            Text(text = when (status) {
                FundingStatus.CANCELED -> stringResource(R.string.funding_cancel_bottom_sheet_title)
                FundingStatus.FAILED -> stringResource(R.string.funding_fail_bottom_sheet_message)
                else -> ""
            })
        }

        DialogLayout(
            showDialog = showDialog,
            confirmBtnText = stringResource(id = R.string.btn_confirm),
            onClickConfirm = {
                showDialog.value = false
            }
        ) {
            HostInfoDialogContent(
                hostInfo = hostInfo,
                description = description
            )
        }
    }
}