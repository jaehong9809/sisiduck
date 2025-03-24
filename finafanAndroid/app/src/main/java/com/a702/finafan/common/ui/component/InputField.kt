package com.a702.finafan.common.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.EditBgGray
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.ErrorTextRed
import com.a702.finafan.common.ui.theme.MainBlack


// 모금통장 메시지, 안내사항, 해지사유 입력 필드 - 박스 길이가 김

// 이메일 입력 필드 (중복확인 버튼도 함께)
@Composable
fun EmailField() {
    var text by remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "이메일",
            color = MainBlack,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(18.dp),
            label = { Text("이메일 아이디 입력") },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = EditTextGray,
                focusedTextColor = MainBlack,
                focusedContainerColor = EditBgGray,
                unfocusedContainerColor = EditBgGray,
                focusedIndicatorColor = EditBgGray,
                unfocusedIndicatorColor = EditBgGray,
                errorIndicatorColor = ErrorTextRed
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(0.dp)
        )

    }
}

// 비밀번호 입력 필드 - 히든으로 보여야함
@Composable
fun PasswordField() {

}

// 전화번호 입력 필드 -> 자동으로 - 추가
@Composable
fun PhoneNumberField() {

}

// 회원 이름, 스타 이름 필드, 응원 메시지(이건 20자 제한), 계좌번호
@Composable
fun NameField() {

}

// 스타 검색 필드 (검색 아이콘 필요)
@Composable
fun SearchField() {

}

// 인증번호, 생년월일 숫자 입력 필드
@Composable
fun NumberField() {

}

// 적금 이름 작성 필드 -> 옆에 적금 텍스트가 붙어 있음
@Composable
fun SavingItemNameField() {
//    TextField()
}

// 금액 입력 필드 - 숫자 -> 옆에 원 텍스트가 붙어 있음, 자동으로 , 추가
@Composable
fun MoneyField() {

}

// 출금 계좌 선택 박스
@Composable
fun SelectAccount() {

}

@Preview(showBackground = true)
@Composable
fun FieldPreview() {
    EmailField()
}