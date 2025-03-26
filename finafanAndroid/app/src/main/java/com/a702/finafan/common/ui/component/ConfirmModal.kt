package com.a702.finafan.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.savings.Star

// 공통 다이얼로그 [ title(선택) ]
@Composable
fun CommonDialog(
    title: String? = null,
    content: String,
    isInfo: Boolean = true,
    confirmBtnText: String = stringResource(R.string.btn_confirm),
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit,
) {
    CommonDialogContent(
        title = title,
        content = content,
        isInfo = isInfo,
        confirmBtnText = confirmBtnText,
        onClickConfirm = onClickConfirm,
        onClickCancel = onClickCancel
    )
}

// 스타 추가 확인 화면
@Composable
fun AddStarDialog(
    star: Star,
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit,
) {
    CommonDialogContent(
        image = star.image,
        content = star.name,
        confirmBtnText = stringResource(R.string.btn_add),
        onClickConfirm = onClickConfirm,
        onClickCancel = onClickCancel
    )
}

@Composable
fun CommonDialogContent(
    title: String? = null,
    content: String? = null,
    image: Any? = null,
    isInfo: Boolean = true,
    confirmBtnText: String,
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit
) {
    Dialog(
        onDismissRequest = { onClickCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MainWhite, shape = RoundedCornerShape(25.dp)),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
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

                image?.let {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it)
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
                }

                content?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        color = MainBlack,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(28.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (isInfo) {
                        CommonCancelButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            onClick = onClickCancel,
                            text = stringResource(R.string.btn_cancel)
                        )
                    }
                    PrimaryGradButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = onClickConfirm,
                        text = confirmBtnText
                    )
                }
            }
        }
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
            onClickConfirm = {  },
            onClickCancel = {  }
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
        onClickConfirm = {  },
        onClickCancel = {  }
    )
}

@Preview
@Composable
fun AddStarDialogPreview() {
    AddStarDialog (
        Star("", "이찬원"),
        onClickConfirm = {  },
        onClickCancel = {  }
    )
}