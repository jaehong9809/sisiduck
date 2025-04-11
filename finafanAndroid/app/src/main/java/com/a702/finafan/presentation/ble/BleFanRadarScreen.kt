package com.a702.finafan.presentation.ble

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
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
import com.a702.finafan.common.ui.theme.BubbleBlue
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainGradBlue
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.ble.model.Fan
import com.a702.finafan.infrastructure.ble.BleScanner
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun BleFanRadarScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BleFanRadarViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val fans = uiState.matchedFans
    val myProfileUrl = uiState.myProfileUrl

    val bleScanner = remember {
        BleScanner(context = context) { uuid ->
            viewModel.addScannedUuid(uuid)
        }
    }

    LaunchedEffect(Unit) {
        bleScanner.start()
    }

    // UUID 수집 후, 서버에 매핑 요청
    LaunchedEffect(viewModel.nearbyUuids) {
        viewModel.nearbyUuids.collect { uuids ->
            if (uuids.isNotEmpty()) {
                val uuidStrings = uuids.map { it.toString() }
                viewModel.matchFans(uuidStrings)
            }
        }
    }

    FanRadarScreen(
        myProfileUrl = myProfileUrl,
        fans = fans,
        modifier = modifier,
        onShowCheerClick = {
            navController.navigate("matched_fan_deposits")
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
        containerColor = MainWhite,

        bottomBar = {
            if (fans.isNotEmpty()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    PrimaryGradButton(
                        text = stringResource(R.string.show_fans_cheers),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        onClick = onShowCheerClick
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

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

                Spacer(Modifier.height(12.dp))

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
                }
            }

            if (fans.isEmpty()) {
                Text(
                    text = stringResource(R.string.finding_now),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
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
    val infiniteTransition = rememberInfiniteTransition()
    val radarOffsetX by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    BoxWithConstraints(
        modifier = modifier.offset(x = radarOffsetX.dp)
    ) {
        val parentRadiusPx = with(LocalDensity.current) {
            min(maxWidth, maxHeight).toPx() / 2f
        }

        // Radar Circles
        Canvas(
            Modifier
                .matchParentSize()
                .scale(1.75f)
        ) {
            val radiusStep = parentRadiusPx / 4f
            repeat(4) { i ->
                drawCircle(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    radius = radiusStep * (i + 1),
                    center = center,
                    style = Stroke(width = 1.dp.toPx())
                )
            }
        }

        // 내 사진
        AnimatedAvatar(
            url = myProfileUrl,
            name = "나",
            modifier = Modifier.align(Alignment.Center)
        )

        // 주변 팬
        fans.forEachIndexed { index, fan ->
            val angle = (index * 60f + 30f) % 360
            val distanceFactor = if (index % 2 == 0) 1.1f else 1.4f
            val radius = parentRadiusPx * distanceFactor
            val offset = polarToOffset(radius, angle)

            AnimatedAvatar(
                url = fan.profileUrl,
                name = fan.name,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
            )
        }
    }
}

@Composable
private fun AnimatedAvatar(
    url: String,
    name: String,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(BubbleBlue.copy(alpha = pulseAlpha))
            )
            AsyncImage(
                model = url,
                contentDescription = name,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
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


@Composable
private fun SpeechBubble(
    text: AnnotatedString,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(MainBgLightGray, RoundedCornerShape(16.dp))
            .padding(horizontal = 28.dp, vertical = 16.dp)
    ) {
        Text(
            text,
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun SpeechBubblePreview() {
    SpeechBubble(
        text = buildAnnotatedString {
            append("주변에 ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("3명의 ")
            }
            append("팬이 있어요!")
        },
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun FanRadarScreenPreview() {
    val heroUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/775e6e08-eb42-4ef7-82b7-84fc5465afb0_1744257521339.jpg"

    val dummyFans = listOf(
        Fan(id = 1, name = "이*호", profileUrl = heroUrl),
        Fan(id = 2, name = "강*주", profileUrl = heroUrl),
    )

    FanRadarScreen(
        myProfileUrl = heroUrl,
        fans = dummyFans,
        onShowCheerClick = {}
    )
}
