package com.a702.finafan.common.ui.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.MainBgGray
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite

// 이미지 추가 버튼
@Composable
fun ImageField(modifier: Modifier = Modifier, label: String,
               selectImage: MutableState<Uri>) {

    val isImageAdd = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.wrapContentWidth()
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = label,
            color = MainBlack,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
            textAlign = TextAlign.Start
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isImageAdd.value) {
                ImageAddButton(selectImage)
            }

            if (isImageAdd.value) {
                // TODO: 갤러리에서 선택한 이미지 보여주기
                ImageInfo(isImageAdd, selectImage)
            }
        }

    }
}

@Composable
fun ImageAddButton(selectImage: MutableState<Uri>) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .wrapContentHeight()
            .background(color = MainBgLightGray,
                shape = RoundedCornerShape(10.dp))
            .clickable {
                // TODO: 사진 권한 확인
                // selectImage에 갤러리에서 받아온 이미지 넣기
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(top = 22.dp),
            painter = painterResource(R.drawable.icon_add_img),
            contentDescription = ""
        )

        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.add_image_label),
            color = MainTextGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ImageInfo(isImageAdd: MutableState<Boolean>, selectImage: MutableState<Uri>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("")
                    .build()
            ),
            contentDescription = "Selected Image",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MainBgLightGray)
        )

        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(56.dp)
                .background(color = MainWhite, shape = RoundedCornerShape(18.dp))
                .border(2.dp, MainBgGray, shape = RoundedCornerShape(18.dp))
                .clickable {
                    /* TODO: 사진 삭제 */
                    isImageAdd.value = false
                    selectImage.value = Uri.EMPTY
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.delete_image_label),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = EditTextGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageAddButtonPreview() {
    ImageField(
        label = stringResource(R.string.saving_item_deposit_image_label),
        selectImage = remember { mutableStateOf(Uri.EMPTY) }
    )
}