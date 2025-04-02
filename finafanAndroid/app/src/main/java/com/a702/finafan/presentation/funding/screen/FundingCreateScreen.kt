package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
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
import com.a702.finafan.common.ui.component.SimpleDropdownMenuExample
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.funding.component.MenuDescription
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.viewmodel.FundingCreateViewModel

@Composable
fun FundingCreateScreen(
    navController: NavHostController,
    fundingCreateViewModel: FundingCreateViewModel
) {
    Column() {
        CommonBackTopBar(text = stringResource(R.string.funding_create_title))
        Column(
            modifier = Modifier.background(color = MainWhite)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            MenuTitle(stringResource(R.string.funding_create_star_label))
            SimpleDropdownMenuExample()

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

            MenuTitle(stringResource(R.string.funding_create_caution_title))
            MenuDescription(stringResource(R.string.funding_create_caution_description))

            MenuTitle(stringResource(R.string.funding_create_content_label))
            MenuDescription(stringResource(R.string.funding_create_content_description))
            val fundingDescription = rememberSaveable { mutableStateOf("") }
            CommonTextArea(
                description = fundingDescription,
                placeholder = stringResource(R.string.funding_create_content_placeholder),
                charLimit = 1000,
                modifier = Modifier
            )
        }
    }


}