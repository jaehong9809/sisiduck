package com.a702.finafan.common.ui.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun MultiImageField(
    modifier: Modifier = Modifier,
    label: String,
    selectImages: SnapshotStateList<Uri>,
    maxImageCount: Int = 5,
    onImagesChanged: (List<Uri>) -> Unit
) {
    val context = LocalContext.current

    Column(modifier = modifier.wrapContentWidth()) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = label,
            color = MainBlack,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
            textAlign = TextAlign.Start
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            items(selectImages.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(selectImages[index]),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(MainBgLightGray),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(20.dp)
                            .background(MainWhite, RoundedCornerShape(10.dp))
                            .clickable {
                                selectImages.removeAt(index)
                                onImagesChanged(selectImages.toList())
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("X", fontSize = 12.sp, color = EditTextGray)
                    }
                }
            }

            if (selectImages.size < maxImageCount) {
                item {
                    AddImageButton { uri ->
                        uri?.let {
                            selectImages.add(it)
                            onImagesChanged(selectImages.toList())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddImageButton(onImageSelected: (Uri?) -> Unit) {
    val context = LocalContext.current

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        onImageSelected(uri)
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MainBgLightGray)
            .clickable {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_add_img),
            contentDescription = "Add Image",
            tint = MainTextGray,
            modifier = Modifier.size(32.dp)
        )
    }
}
