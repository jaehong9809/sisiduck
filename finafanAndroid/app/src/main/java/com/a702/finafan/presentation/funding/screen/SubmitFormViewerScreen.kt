
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.theme.CustomTypography.bodySmall
import com.a702.finafan.common.ui.theme.CustomTypography.displaySmall
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.TermBoxGray
import com.a702.finafan.common.ui.theme.Typography
import com.a702.finafan.presentation.funding.component.FundingInfoHeader
import com.a702.finafan.presentation.funding.component.MenuDescription
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.component.SuccessBadge
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel
import com.a702.finafan.presentation.funding.viewmodel.SubmitFormViewModel

@Composable
fun SubmitFormViewerScreen(
    navController: NavController,
    fundingDetailViewModel: FundingDetailViewModel,
    submitFormViewModel: SubmitFormViewModel = hiltViewModel()
) {
    val fundingState by fundingDetailViewModel.uiState.collectAsState()
    val submitFormState by submitFormViewModel.submitFormState.collectAsState()

    Scaffold(
        topBar = {
            CommonBackTopBar(
                text = "종료된 모금 상세"
            )
        },
        containerColor = MainWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            fundingState.funding?.let {
                FundingInfoHeader(
                    funding = it,
                    showRemainingAmount = false,
                    showDetailButton = false
                )
            }

            SuccessBadge(
                size = 120.dp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)
            )

            // 증빙 이미지 섹션
            MenuTitle(content = "증빙 서류 첨부", modifier = Modifier.padding(vertical = 10.dp))
            MenuDescription(content = "지출한 금액에 대한 증빙 자료입니다.")
            submitFormState.imageList.forEach { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Spacer(Modifier.height(30.dp))

            // 지출 내역
            MenuTitle(content = "지출 내역", modifier = Modifier.padding(vertical = 10.dp))
            MenuDescription(content = "실제 사용된 지출 내역입니다.")
            submitFormState.usageList.forEach { (title, amount) ->
                Text("• $title : $amount", style = Typography.bodyMedium)
            }

            Spacer(Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = TermBoxGray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(5.dp),
                ) {
                    Text("📌 참고", style = displaySmall)
                    Text(
                        "해당 내용은 모금 종료 시 작성된 정보를 바탕으로 자동 저장되었습니다.",
                        style = bodySmall
                    )
                }
            }

            Spacer(Modifier.height(30.dp))

            // 안내 사항
            MenuTitle(content = "안내 사항", modifier = Modifier.padding(vertical = 10.dp))
            MenuDescription(content = "참가자들에게 전달된 메시지입니다.")
            Text(
                text = submitFormState.content.ifEmpty { "작성된 안내 사항이 없습니다." },
                style = Typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .background(
                        color = TermBoxGray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            )
        }
    }
}
