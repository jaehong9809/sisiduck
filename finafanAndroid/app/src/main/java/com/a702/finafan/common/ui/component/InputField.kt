package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.EditBgGray
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil.formatEditMoney


@Composable
fun TextItem(text: String, color: Color, fontSize: TextUnit) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    label: String,
    hint: String,
    text: MutableState<String>,
    isNumber: Boolean = false,
    isMoney: Boolean = false,
    isSaving: Boolean = false,
    isSignUp: Boolean = false,
    maxLength: Int = 0,
    onClick: (() -> Unit)? = null,
    isPassword: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MainWhite)
    ) {
        TextItem(label, MainBlack, 16.sp)

        BasicTextField(
            value = text.value,
            onValueChange = { newText ->
                if (maxLength == 0 || newText.length <= maxLength) {
                    if (isMoney) {
                        text.value = formatEditMoney(newText.toInt())
                    } else {
                        text.value = newText
                    }
                }
            },
            textStyle = TextStyle(color = MainBlack, fontSize = 20.sp),
            keyboardOptions = KeyboardOptions(
                keyboardType =
                    if (isPassword) KeyboardType.Password
                    else if(isNumber) KeyboardType.Number
                    else KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
                        .padding(all = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (text.value.isEmpty()) {
                            TextItem(hint, EditTextGray, 20.sp)
                        }
                        innerTextField()
                    }

                    // 회원가입 시에만 중복확인 버튼 보여줌
                    if (isSignUp) {
                        Spacer(modifier = Modifier.width(width = 8.dp))

                        Box(
                            modifier = Modifier
                                .width(66.dp)
                                .height(28.dp)
                                .clickable { onClick?.invoke() }
                                .background(color = MainWhite, shape = RoundedCornerShape(10.dp))
                                .border(1.dp, EditTextGray, shape = RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "중복확인",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = EditTextGray
                            )
                        }
                    }

                    if (isMoney) {
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Text(
                            text = "원",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MainBlack
                        )
                    }

                    if (isSaving) {
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Text(
                            text = "적금",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MainBlack
                        )
                    }
                }
            }
        )
    }
}


// 이메일 입력 필드 (중복확인 버튼도 함께)
@Composable
fun EmailField(isSignUp: Boolean = false, onClick: (() -> Unit)? = null) {
    val text = remember { mutableStateOf("") }

    CommonTextField(
        label = "이메일",
        hint = "이메일 입력",
        text = text,
        isSignUp = isSignUp,
        onClick = onClick
    )

//    var text by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MainWhite)
//    ) {
//        TextItem("이메일", MainBlack, 16.sp)
//
//        BasicTextField(
//            value = text,
//            onValueChange = { text = it },
//            textStyle = TextStyle(color = MainBlack, fontSize = 20.sp),
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Done
//            ),
//            decorationBox = { innerTextField ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
//                        .padding(all = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Box(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        if (text.isEmpty()) {
//                            TextItem("이메일 입력", EditTextGray, 20.sp)
//                        }
//
//                        innerTextField()
//                    }
//
//                    Spacer(modifier = Modifier.width(width = 8.dp))
//
//                    // 회원가입 시에만 중복확인 버튼 보여줌
//                    if (!isLogin) {
//                        Box(
//                            modifier = Modifier
//                                .width(66.dp)
//                                .height(28.dp)
//                                .clickable {
//                                    // 회원가입 시 이메일 중복확인
//                                    onClick?.invoke()
//                                }
//                                .background(color = MainWhite, shape = RoundedCornerShape(10.dp))
//                                .border(1.dp, EditTextGray, shape = RoundedCornerShape(10.dp)),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = "중복확인",
//                                fontSize = 12.sp,
//                                fontWeight = FontWeight.Medium,
//                                color = EditTextGray
//                            )
//                        }
//                    }
//
//                }
//            },
//        )
//
//    }
}

// 비밀번호 입력 필드
@Composable
fun PasswordField(label: String, hint: String, text: MutableState<String>) {
    CommonTextField(
        label = label,
        hint = hint,
        text = text,
        isPassword = true
    )

//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MainWhite)
//    ) {
//        TextItem(label, MainBlack, 16.sp)
//
//        BasicTextField(
//            value = text.value,
//            onValueChange = { text.value = it },
//            textStyle = TextStyle(color = MainBlack, fontSize = 20.sp),
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Password,
//                imeAction = ImeAction.Done
//            ),
//            decorationBox = { innerTextField ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
//                        .padding(all = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Box(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        if (text.value.isEmpty()) {
//                            TextItem(hint, EditTextGray, 20.sp)
//                        }
//                        innerTextField()
//                    }
//
//                    Spacer(modifier = Modifier.width(width = 8.dp))
//                }
//            },
//        )
//
//    }
}

// 스타 검색 필드 (검색 아이콘 필요)
@Composable
fun SearchField(onClick: () -> Unit) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = text,
        onValueChange = { text = it },
        textStyle = TextStyle(color = MainBlack, fontSize = 20.sp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()

                onClick() // 검색
            }
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (text.isEmpty()) {
                        TextItem("스타 검색", EditTextGray, 20.sp)
                    }
                    innerTextField()
                }

                Spacer(modifier = Modifier.width(width = 8.dp))

                Icon(
                    painter = painterResource(R.drawable.icon_search),
                    contentDescription = "",
                    tint = EditTextGray,
                    modifier = Modifier.clickable {
                        keyboardController?.hide()

                        onClick() // 검색
                    }
                )
            }
        },
    )
}

// 숫자 입력 필드
// 인증번호, 생년월일, 금액, 전화번호
@Composable
fun NumberField(modifier: Modifier = Modifier,
                label: String, hint: String,
                text: MutableState<String>,
                isMoney: Boolean = false,
                maxLength: Int = 0) {

    CommonTextField(
        modifier = modifier,
        label = label,
        hint = hint,
        text = text,
        isMoney = isMoney,
        maxLength = maxLength
    )

//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(MainWhite)
//    ) {
//        TextItem(label, MainBlack, 16.sp)
//
//        BasicTextField(
//            value = text.value,
//            onValueChange = { newText ->
//                if (maxLength == 0 || newText.length <= maxLength) {
//                    if (isMoney) {
//                        formatEditMoney(newText.toInt())
//                    }
//
//                    text.value = newText
//                }
//            },
//            textStyle = TextStyle(color = MainBlack, fontSize = 20.sp),
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Done
//            ),
//            decorationBox = { innerTextField ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
//                        .padding(all = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Box(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        if (text.value.isEmpty()) {
//                            TextItem(hint, EditTextGray, 20.sp)
//                        }
//                        innerTextField()
//                    }
//
//                    if (isMoney) {
//                        Spacer(modifier = Modifier.width(width = 8.dp))
//
//                        Text(
//                            text = "원",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Medium,
//                            color = MainBlack
//                        )
//                    }
//
//
//                }
//            },
//        )
//
//    }
}

// 문자 입력 필드
// 이름, 적금이름, 응원메시지, 모금페이지에 쓰이는 필드
// 계좌번호, 스타이름
@Composable
fun SavingItemNameField(modifier: Modifier = Modifier,
                        label: String, hint: String,
                        text: MutableState<String>,
                        isSaving: Boolean = false,
                        maxLength: Int = 0) {

    CommonTextField(
        modifier = modifier,
        label = label,
        hint = hint,
        text = text,
        isSaving = isSaving,
        maxLength = maxLength
    )

//    var text by remember { mutableStateOf("") }

//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(MainWhite)
//    ) {
//        TextItem(label, MainBlack, 16.sp)
//
//        BasicTextField(
//            value = text.value,
//            onValueChange = { newText ->
//                if (maxLength == 0 || newText.length <= maxLength) {
//                    text.value = newText
//                }
//            },
//            textStyle = TextStyle(color = MainBlack, fontSize = 20.sp),
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Done
//            ),
//            decorationBox = { innerTextField ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
//                        .padding(all = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Box(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        if (text.value.isEmpty()) {
//                            TextItem(hint, EditTextGray, 20.sp)
//                        }
//                        innerTextField()
//                    }
//
//                    if (isSaving) {
//                        Spacer(modifier = Modifier.width(width = 8.dp))
//
//                        Text(
//                            text = "적금",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Medium,
//                            color = MainBlack
//                        )
//                    }
//
//
//                }
//            },
//        )
//
//    }
}

// 출금 계좌 선택 박스
@Composable
fun SelectAccount() {

}

@Preview(showBackground = true)
@Composable
fun FieldPreview() {
    val text = remember { mutableStateOf("") }
    SavingItemNameField(label = "이름", hint = "이름 입력", text = text)
}