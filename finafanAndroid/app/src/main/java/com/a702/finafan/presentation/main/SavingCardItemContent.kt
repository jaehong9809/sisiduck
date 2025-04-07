package com.a702.finafan.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.AccountTextGary
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainGradBlue
import com.a702.finafan.common.ui.theme.MainGradViolet
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.main.model.MainSaving
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun LoginContent(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "",
            tint = Color.Unspecified,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp)
                .background(MainBgLightGray, shape = RoundedCornerShape(15.dp))
                .clickable {
                    navController.navigate("login")
                },
            contentAlignment = Alignment.Center
        ){
            Text(text = stringResource(R.string.card_login_button),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                fontFamily = Pretendard,
                color = MainBlack
            )
        }
    }
}

@Composable
fun CreateSavingContent() {

    val navController = LocalNavController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(),
    ) {
        Icon(
                painter = painterResource(id = R.drawable.opened_box),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 25.dp)
            )
        Text(text = stringResource(R.string.card_create_saving_info),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MainTextGray,
            fontFamily = Pretendard,
            modifier = Modifier.padding(top = 20.dp, bottom = 13.dp))
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(MainGradBlue, MainGradViolet)
                    )
                )
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    navController.navigate(NavRoutes.SavingDesc.route)
                           },
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.card_create_saving_button),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainWhite)
        }
    }
}

@Composable
fun SavingContent(saving: MainSaving) {

    val navController = LocalNavController.current

    Column(
        modifier = Modifier.fillMaxHeight()
            .clickable{
                navController.navigate(NavRoutes.SavingMain.route + "/" + saving.savingId)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(140.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, start = 20.dp)
            ) {
                Row(){
                    Text(text = saving.starName,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = MainBlack)
                    Text(text = stringResource(R.string.card_saving_info),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = MainBlack)
                }
                Text(text = saving.accountNo,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = AccountTextGary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(vertical = 3.dp)
                )
                Text(text = StringUtil.formatCurrency(saving.amount),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = MainBlack,
                    modifier = Modifier.padding(vertical = 20.dp))
            }
            Image(
                painter = rememberAsyncImagePainter(saving.starImageUrl),
                contentDescription = saving.starName,
                modifier = Modifier
            )
        }
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(MainGradBlue, MainGradViolet)
                    )
                )
                .fillMaxWidth()
                .height(60.dp)
                .clickable{
                    navController.navigate(NavRoutes.SavingDeposit.route + "/" + saving.savingId)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.card_saving_deposit_button),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainWhite)
        }
    }
}
