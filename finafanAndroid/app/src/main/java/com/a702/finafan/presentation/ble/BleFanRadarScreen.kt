package com.a702.finafan.presentation.ble

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradButton
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.ble.model.Fan
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun BleFanRadarScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BleFanRadarViewModel = hiltViewModel()
) {
    val myProfileUrl by viewModel.myProfileUrl.collectAsState()
    val fans by viewModel.fans.collectAsState()

    FanRadarScreen(
        myProfileUrl = myProfileUrl,
        fans = fans,
        modifier = modifier,
        onShowCheerClick = {
            // 응원 팬 보기
        }
    )
}


@Composable
fun FanRadarScreen(
    myProfileUrl: String,
    fans: List<Fan>,
    modifier: Modifier = Modifier,
    onShowCheerClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CommonBackTopBar(
                text = stringResource(R.string.find_fan_radar),
                isTextCentered = true
            )
        },
        containerColor = MainWhite
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Radar(
                    modifier = Modifier.fillMaxSize(),
                    myProfileUrl = myProfileUrl,
                    fans = fans
                )

                if (fans.isNotEmpty()) {
                    SpeechBubble(
                        text = buildAnnotatedString {
                            append("주변에 ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("${fans.size}명의 ")
                            }
                            append("팬이 있어요!")
                        },
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 72.dp)
                    )
                } else {
                    Text(
                        text = stringResource(R.string.finding_now),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (fans.isNotEmpty()) {
                PrimaryGradButton(
                    text = stringResource(R.string.show_fans_cheers),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(bottom = 24.dp),
                    onClick = onShowCheerClick
                )
            }
        }
    }
}


@Composable
private fun Radar(
    modifier: Modifier = Modifier,
    myProfileUrl: String,
    fans: List<Fan>
) {
    BoxWithConstraints(modifier) {
        val parentRadiusPx = with(LocalDensity.current) {
            min(maxWidth, maxHeight).toPx() / 2f
        }

        // circles
        Canvas(
            Modifier
                .matchParentSize()
                .scale(1.75f)
        ) {
            val radiusStep = parentRadiusPx / 4f
            repeat(4) { i ->
                drawCircle(
                    color  = MainBgLightGray,
                    radius = radiusStep * (i + 1),
                    center = center,
                    style  = Stroke(width = 1.dp.toPx())
                )
            }
        }

        // 내 사진
        Avatar(
            url = myProfileUrl,
            label = "나",
            modifier = Modifier.align(Alignment.Center)
        )

        // 주변 팬
        fans.forEachIndexed { index, fan ->
            val angle = (index * 60f + 30f) % 360      // 예시 각도
            val distanceFactor = if (index % 2 == 0) 0.5f else 0.8f
            val radius = parentRadiusPx * distanceFactor

            val offset = polarToOffset(radius, angle)
            Avatar(
                url = fan.profileUrl,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
            )
        }
    }
}


// ---------- Avatar(Profile) ----------
@Composable
private fun Avatar(
    url: String,
    label: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = url,
            contentDescription = label ?: "fan",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


// ---------- Util ----------
private fun polarToOffset(r: Float, deg: Float): Offset {
    val rad = Math.toRadians(deg.toDouble())
    return Offset(
        (r * cos(rad)).toFloat(),
        (r * sin(rad)).toFloat()
    )
}


// ---------- SpeechBubble ----------
@Composable
private fun SpeechBubble(
    text: AnnotatedString,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
