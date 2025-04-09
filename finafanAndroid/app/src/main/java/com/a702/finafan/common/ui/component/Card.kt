package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Pretendard

@Composable
fun Card(
    content: String,
    modifier: Modifier
){
    Box(
        modifier
            .fillMaxWidth()
            .heightIn()
            .shadow(10.dp, spotColor = MainBlack.copy(alpha = 0.05f), shape = RoundedCornerShape(20.dp))
            .background(MainWhite)
            .padding(20.dp)
    ) {
        Text(content, fontSize = 20.sp, fontWeight = FontWeight.Medium, fontFamily = Pretendard,
            color = MainBlack, lineHeight = 20.sp)
    }
}

@Composable
fun TermsExpandableCard(
    shortSummary: String,
    fullText: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier
        .fillMaxWidth()
        .heightIn()
        .shadow(10.dp, spotColor = MainBlack.copy(alpha = 0.05f), shape = RoundedCornerShape(20.dp))
        .background(MainWhite)
        .padding(20.dp)) {

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (expanded) fullText else shortSummary,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = if (expanded) "간단히 보기" else "전문 보기",
            modifier = Modifier
                .align(Alignment.End)
                .clickable { expanded = !expanded }
                .padding(top = 8.dp),
            color = MainTextBlue,
            fontWeight = FontWeight.Bold
        )
    }
}
