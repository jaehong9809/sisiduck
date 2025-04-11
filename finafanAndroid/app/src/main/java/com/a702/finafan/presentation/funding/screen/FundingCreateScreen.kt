package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import com.a702.finafan.R
import com.a702.finafan.common.constants.Terms
import com.a702.finafan.common.ui.component.Badge
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.component.DatePickerView
import com.a702.finafan.common.ui.component.DialogLayout
import com.a702.finafan.common.ui.component.LiveTextArea
import com.a702.finafan.common.ui.component.LiveTextField
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.theme.CustomTypography.bodySmall
import com.a702.finafan.common.ui.theme.CustomTypography.displaySmall
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.TermBoxGray
import com.a702.finafan.presentation.funding.component.MenuDescription
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.component.MyStarRow
import com.a702.finafan.presentation.funding.viewmodel.FundingCreateViewModel
import com.a702.finafan.presentation.navigation.NavRoutes
import java.time.LocalDate

@Composable
fun FundingCreateScreen(
    navController: NavHostController,
    fundingCreateViewModel: FundingCreateViewModel
) {
    val uiState by fundingCreateViewModel.uiState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val showTermsDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        fundingCreateViewModel.fetchMyStars()
        fundingCreateViewModel.updateSelectedStar(null)
    }

    val myStars = uiState.myStars
    val title = remember { mutableStateOf("") }
    val goalAmount = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val expiryDate = remember { mutableStateOf<LocalDate?>(null) }

    Scaffold(
        topBar = {
            CommonBackTopBar(text = stringResource(R.string.funding_create_title))
        },
        bottomBar = {
            PrimaryGradBottomButton(
                text = "개설",
                isEnabled = uiState.isFormValid,
                onClick = {
                    fundingCreateViewModel.createFunding()
                    showDialog.value = true
                }
            )
        },
        containerColor = MainWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MenuTitle(stringResource(R.string.funding_create_star_label))
            MyStarRow(
                myStars = myStars,
                selectedStar = uiState.selectedStar,
                onStarSelected = { fundingCreateViewModel.updateSelectedStar(it) },
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Spacer(Modifier.height(15.dp))

            MenuTitle(stringResource(R.string.funding_create_title_label))
            MenuDescription(stringResource(R.string.funding_create_title_description))
            LiveTextField(
                value = title.value,
                hint = stringResource(R.string.funding_create_title_placeholder),
                onValueChange = {
                    title.value = it
                    fundingCreateViewModel.updateFundingTitle(it)
                }
            )

            Spacer(Modifier.height(45.dp))

            MenuTitle(stringResource(R.string.funding_create_goal_amount_label))
            MenuDescription(stringResource(R.string.funding_create_goal_amount_description))
            LiveTextField(
                value = goalAmount.value,
                hint = "",
                isMoney = true,
                onValueChange = {
                    goalAmount.value = it
                    fundingCreateViewModel.updateFundingGoal(it)
                }
            )

            Spacer(Modifier.height(45.dp))

            DatePickerView(
                label = stringResource(R.string.funding_create_goal_date_label),
                desc = stringResource(R.string.funding_create_goal_date_description),
                selectedDate = expiryDate.value,
                onDateSelected = {
                    expiryDate.value = it
                    fundingCreateViewModel.updateFundingExpiryDate(it)
                }
            )

            Spacer(Modifier.height(10.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = TermBoxGray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(stringResource(R.string.funding_create_caution_title), style = displaySmall)
                    Text(stringResource(R.string.funding_create_caution_description),
                        style = bodySmall)
                }
            }

            Spacer(Modifier.height(45.dp))

            MenuTitle(stringResource(R.string.funding_create_content_label))
            MenuDescription(stringResource(R.string.funding_create_content_description))
            LiveTextArea(
                description = description,
                placeholder = stringResource(R.string.funding_create_content_placeholder),
                charLimit = 1000,
                onValueChange = {
                    description.value = it
                    fundingCreateViewModel.updateFundingDescription(it)
                }
            )

            Spacer(Modifier.height(45.dp))

            MenuTitle(stringResource(R.string.funding_create_term_title))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTermsDialog.value = true }
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.isTermAgreed,
                    onCheckedChange = { fundingCreateViewModel.updateTermAgreed(it) },
                    modifier = Modifier.size(30.dp)
                )
                Spacer(Modifier.width(10.dp))
                MenuDescription("모금 통장 이용 약관 동의")
                Spacer(Modifier.width(10.dp))
                Badge(content = "자세히 보기", fontColor = MainWhite, bgColor = MainTextBlue)
            }

            if (showTermsDialog.value) {
                DialogLayout(
                    showDialog = showTermsDialog,
                    isConfirm = true,
                    confirmBtnText = "동의",
                    onClickConfirm = {
                        showTermsDialog.value = false
                        fundingCreateViewModel.updateTermAgreed(true)
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "모금 통장 이용 약관",
                            style = displaySmall,
                            modifier = Modifier.padding(5.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = Terms.FUNDING_TERMS,
                            modifier = Modifier
                                .height(500.dp)
                                .verticalScroll(rememberScrollState())
                                .padding(end = 4.dp),
                            style = bodySmall
                        )
                    }
                }
            }


            Spacer(Modifier.height(80.dp))
        }
    }

    if (showDialog.value) {
        ConfirmDialog(
            showDialog = showDialog,
            content = "펀딩이 성공적으로 생성되었습니다!",
            onClickConfirm = {
                showDialog.value = false
                navController.navigate(NavRoutes.Funding.route) {
                    popUpTo(0)
                }
            }
        )
    }
}
