package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonTextArea
import com.a702.finafan.common.ui.component.CommonTextField
import com.a702.finafan.common.ui.component.DatePicker
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.TermBoxGray
import com.a702.finafan.domain.funding.model.MyStar
import com.a702.finafan.presentation.funding.component.MenuDescription
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.viewmodel.FundingCreateViewModel

@Composable
fun FundingCreateScreen(
    navController: NavHostController,
    fundingCreateViewModel: FundingCreateViewModel
) {

    val uiState by fundingCreateViewModel.uiState.collectAsState()

    val myStars = listOf(
        MyStar(
            id = 1,
            name = "앙앙",
            imageUrl = "https://cdn.eroun.net/news/photo/202305/32650_59862_4410.jpg"
        )
    )

    Column() {
        CommonBackTopBar(text = stringResource(R.string.funding_create_title))
        Column(
            modifier = Modifier.background(color = MainWhite)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            MenuTitle(stringResource(R.string.funding_create_star_label))
//            MyStarRow(
//                myStars = myStars,
//                selectedStar = uiState.selectedStar,
//                onStarSelected = { uiState.selectedStar = it }
//            )



            MenuTitle(stringResource(R.string.funding_create_title_label))
            MenuDescription(stringResource(R.string.funding_create_title_description))
            val fundingTitle = rememberSaveable { mutableStateOf("") }
            CommonTextField(
                text = fundingTitle,
                hint = stringResource(R.string.funding_create_title_placeholder),
                isSaving = false
            )

            MenuTitle(stringResource(R.string.funding_create_goal_amount_label))
            MenuDescription(stringResource(R.string.funding_create_goal_amount_description))
            val fundingGoal = rememberSaveable { mutableStateOf("") }
            CommonTextField(
                text = fundingGoal,
                hint = "",
                isMoney = true
            )

            MenuTitle(stringResource(R.string.funding_create_goal_date_label))
            DatePicker { }

            Box(
                modifier = Modifier.background(color = TermBoxGray)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    MenuTitle(stringResource(R.string.funding_create_caution_title))
                    MenuDescription(stringResource(R.string.funding_create_caution_description))
                }
            }

            MenuTitle(stringResource(R.string.funding_create_content_label))
            MenuDescription(stringResource(R.string.funding_create_content_description))
            val fundingDescription = rememberSaveable { mutableStateOf("") }
            CommonTextArea(
                description = fundingDescription,
                placeholder = stringResource(R.string.funding_create_content_placeholder),
                charLimit = 1000,
                modifier = Modifier
            )
            PrimaryGradBottomButton(
                modifier = Modifier,
                onClick = { /* createFunding() */ },
                text = "다음",
                isEnabled = true
            )
        }
    }
}