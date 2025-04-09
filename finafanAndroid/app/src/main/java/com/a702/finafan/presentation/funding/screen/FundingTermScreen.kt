package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel

@Composable
fun FundingTermScreen(
    navController: NavController,
    fundingId: Long,
) {
    val viewModel: FundingDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val showDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainWhite)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MainWhite)
        ) {
            CommonBackTopBar(
                modifier = Modifier,
                text = "상품 이용 약관",
                isTextCentered = true
            )
            Box(
                modifier = Modifier.padding(25.dp)
            ) {
                Text("이용 약관 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관" +
                        " 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관" +
                        " 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관",
                    fontSize = 20.sp,
                    color = MainBlack
                    )
            }
        }
        PrimaryGradBottomButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onClick = {
                viewModel.joinFunding(fundingId)
            },
            text = stringResource(R.string.btn_create)
        )
        
        if(showDialog.value) {
            ConfirmDialog(
                showDialog = showDialog,
                content = stringResource(R.string.funding_join_alert),
                isConfirm = true,
                onClickConfirm = {
                    showDialog.value = false
                    navController.popBackStack()
                }
            )
        }
    }
    LaunchedEffect(uiState.isParticipant) {
        if (uiState.isParticipant) {
            showDialog.value = true
            navController.popBackStack()
        }
    }
}