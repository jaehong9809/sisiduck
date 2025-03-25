package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.SubButton
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun SavingAccountInfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .windowInsetsPadding(WindowInsets.ime)
    ) {
        // 공통 상단 바
        CommonBackTopBar(
            modifier = Modifier,
            imageOnClick = { /* TODO: 뒤로 가기 */ },
            text = stringResource(R.string.saving_account_manage_title),
            isTextCentered = true
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .background(MainWhite)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("")
                        .build()
                )

                Image(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(LightGray),
                    painter = painter,
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier.padding(top = 54.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "이찬원 적금",
                            color = MainBlack,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp,
                        )

                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = "123-456-789000",
                            color = MainTextGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 24.sp,
                        )
                    }

                    SubButton(
                        stringResource(R.string.saving_item_change_name_label),
                        onButtonClick = { /* TODO: 이름 변경 바텀시트 띄우기 */ })
                }

                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MainBgLightGray)
                )

                AccountDetailList()
            }

            Box(
                modifier = Modifier
                    .padding(top = 34.dp)
                    .fillMaxWidth()
                    .height(18.dp)
                    .background(MainBgLightGray)
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.btn_account_cancel),
                color = MainTextGray,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
            )
        }

    }
}

@Composable
fun AccountDetailList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductContentItem(stringResource(R.string.saving_item_product_name), "스타적금")
        ProductContentItem(stringResource(R.string.saving_item_start_date), "2025.03.18")
        ProductContentItem(stringResource(R.string.saving_item_connect_account), "NH농협 312-0139-3754-31")
        ProductContentItem(stringResource(R.string.saving_item_apply_interest), "1.80%")
    }
}

@Preview
@Composable
fun SavingAccountInfoPreview() {
    SavingAccountInfoScreen()
}