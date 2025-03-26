package com.a702.finafan.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.presentation.savings.Star

// 공통 다이얼로그
@Composable
fun CommonDialog(
    title: String? = null,
    content: String,
    isInfo: Boolean = true,
    confirmBtnText: String = stringResource(R.string.btn_confirm),
    onClickConfirm: () -> Unit
) {
    DialogLayout(
        isInfo = isInfo,
        confirmBtnText = confirmBtnText,
        onClickConfirm = onClickConfirm
    ) {
        title?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MainBlack,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = content,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = MainBlack,
            fontSize = 20.sp
        )
    }
}

// 스타 추가 다이얼로그
@Composable
fun AddStarDialog(
    star: Star,
    onClickConfirm: () -> Unit
) {
    DialogLayout(
        confirmBtnText = stringResource(R.string.btn_add),
        onClickConfirm = onClickConfirm
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(star.image)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "Background Image",
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
                .background(LightGray)
                .border(1.dp, color = MainBlack, shape = CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = star.name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = MainBlack,
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
fun DialogPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CommonDialog(
            content = "내용",
            onClickConfirm = {  }
        )
    }
}

@Preview
@Composable
fun AddDialogPreview() {
    CommonDialog(
        title = "제목",
        content = "내용",
        isInfo = false,
        confirmBtnText = stringResource(R.string.btn_add),
        onClickConfirm = {  }
    )
}

@Preview
@Composable
fun AddStarDialogPreview() {
    AddStarDialog (
        Star("", "이찬원"),
        onClickConfirm = {  }
    )
}