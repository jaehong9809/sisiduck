//import android.net.Uri
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import com.a702.finafan.common.ui.component.CommonBackTopBar
//import com.a702.finafan.common.ui.component.CustomGradBottomButton
//import com.a702.finafan.common.ui.component.LiveTextArea
//import com.a702.finafan.common.ui.component.MultiImageField
//import com.a702.finafan.common.ui.theme.MainGradBlue
//import com.a702.finafan.common.ui.theme.MainGradViolet
//import com.a702.finafan.presentation.funding.component.SpendingListSection
//
//@Composable
//fun SubmitFormReadOnlyScreen(
//    profileImageUrl: String,
//    nickname: String,
//    title: String,
//    uploadedImages: List<Uri>,
//    spendingItems: List<SpendingItem>,
//    description: String,
//    onClickBack: () -> Unit,
//    onClickDone: () -> Unit,
//) {
//    Scaffold(
//        topBar = {
//            CommonBackTopBar(
//                text = "달성 모금 진행 상황"
//            )
//        },
//        bottomBar = {
//            CustomGradBottomButton(
//                onClick = onClickDone,
//                text = "완료",
//                gradientColor = listOf(MainGradBlue, MainGradViolet)
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .padding(horizontal = 20.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//
//            // 상단 프로필 영역
//            Row(
//                modifier = Modifier.padding(top = 20.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                AsyncImage(
//                    model = profileImageUrl,
//                    contentDescription = "Profile Image",
//                    modifier = Modifier
//                        .size(48.dp)
//                        .clip(CircleShape)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Box(
//                    modifier = Modifier
//                        .background(Color(0xFF00C896), RoundedCornerShape(20.dp))
//                        .padding(horizontal = 10.dp, vertical = 4.dp)
//                ) {
//                    Text(text = nickname, color = Color.White, fontSize = 12.sp)
//                }
//            }
//
//            // 제목
//            Text(
//                text = title,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 10.dp)
//            )
//
//            // 증빙 서류
//            SectionTitle(title = "증빙 서류", description = "지출한 금액에 대한 증빙 서류입니다.")
//            MultiImageField(
//                label = "",
//                selectImages = uploadedImages,
//                readOnly = true // 클릭 이벤트 막기
//            )
//
//            // 지출 내역
//            SectionTitle(title = "지출 내역", description = "선지출 및 추후에 지출할 내역입니다.")
//            SpendingListSection(
//                items = spendingItems,
//                onItemsChanged = {}, // 무시
//                isReadOnly = true,
//                modifier = Modifier.padding(top = 10.dp)
//            )
//
//            // 안내 사항
//            SectionTitle(title = "안내 사항", description = "진행자가 참가자들에게 전달한 사항입니다.")
//            LiveTextArea(
//                placeholder = "",
//                description = description,
//                onValueChange = {},
//                readOnly = true,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 10.dp)
//            )
//
//            Spacer(modifier = Modifier.height(80.dp))
//        }
//    }
//}
//
//@Composable
//fun SectionTitle(title: String, description: String) {
//    Column(modifier = Modifier.padding(top = 30.dp)) {
//        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//        Text(
//            text = description,
//            fontSize = 14.sp,
//            color = Color.Gray,
//            modifier = Modifier.padding(top = 4.dp)
//        )
//    }
//}