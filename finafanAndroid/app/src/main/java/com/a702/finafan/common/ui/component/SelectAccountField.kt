package com.a702.finafan.common.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.EditBgGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.account.model.Account
import com.a702.finafan.domain.account.model.Bank
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel

// 출금 계좌 선택 박스
@Composable
fun SelectAccountField(
    viewModel: AccountViewModel,
    accounts: List<Account>
) {

    val accountState by viewModel.accountState.collectAsState()
    var expandStatus by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
            .clickable {
                expandStatus = true
            }
    ) {
        Row(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (accounts.isEmpty()) {
                TextItem(stringResource(R.string.saving_item_connect_account_empty), MainBlack, 20.sp)
            } else {
                TextItem(accountState.selectAccount.bank.bankName + " " + accountState.selectAccount.accountNo,
                    MainBlack, 20.sp)
            }

            Spacer(modifier = Modifier.width(width = 8.dp))

            val rotationAngle by animateFloatAsState(
                targetValue = if (expandStatus) -180f else 0f,
                animationSpec = tween(durationMillis = 300)
            )

            Icon(
                painter = painterResource(R.drawable.angle_down),
                contentDescription = "",
                modifier = Modifier
                    .graphicsLayer(rotationZ = rotationAngle)
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(MainWhite)
                .wrapContentSize(),
            expanded = expandStatus,
            onDismissRequest = {
                expandStatus = false
            }
        ) {
            accounts.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = {
                        Text(
                            text = item.bank.bankName + " " + item.accountNo,
                            fontSize = 20.sp,
                            color = MainBlack,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    onClick = {
                        viewModel.updateSelectAccount(item)
                        expandStatus = false
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAccountPreview() {
    val accounts = mutableListOf(
        Account(1, "123-456", Bank(1, "123", "NH농협"))
    )

//    SelectAccountField(accounts = accounts)
}