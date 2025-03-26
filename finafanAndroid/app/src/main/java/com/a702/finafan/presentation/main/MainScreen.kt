package com.a702.finafan.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.a702.finafan.common.ui.component.MainSquareIconButton
import com.a702.finafan.common.ui.component.MainWideIconButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        MainSquareIconButton(
            onClick = {
                navController.navigate(NavRoutes.SavingMain.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
            },
            text = "적금"
        )

        Spacer(modifier = Modifier.padding(8.dp))

        MainWideIconButton(
            onClick = {
                navController.navigate(NavRoutes.Chat.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
            },
            text = "덕순이"
        )
    }

    // ChatScreen for Testing STT
//    val chatViewModel: ChatViewModel = hiltViewModel()
//    Box(
//        modifier = Modifier
//    ) {
//        ChatScreen(viewModel = chatViewModel)
//    }
}

@Preview
@Composable
fun MainPreview() {
    MainScreen(rememberNavController(), Modifier)
}