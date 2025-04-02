package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.EditBgGray
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil

// 이메일 입력 필드 (회원가입 시 중복확인 버튼)
@Composable
fun EmailField(isSignUp: Boolean = false, text: MutableState<String>, onClick: (() -> Unit)? = null,) {
    CommonTextField(
        label = "이메일",
        hint = "이메일 입력",
        text = text,
        isSignUp = isSignUp,
        onClick = onClick
    )
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
}

// 스타 검색 필드
@Composable
fun SearchField(modifier: Modifier = Modifier, text: MutableState<String>, onClick: () -> Unit) {
    CommonTextField(
        modifier = modifier,
        hint = "스타 검색",
        text = text,
        isSearch = true,
        onClick = onClick
    )
}

/*
    숫자 입력 필드
    인증번호, 생년월일, 금액, 전화번호
*/
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
        isNumber = true,
        maxLength = maxLength
    )
}

/*
    문자 입력 필드
    이름, 적금이름, 응원메시지, 모금페이지에 쓰이는 필드
    계좌번호, 스타이름
*/
@Composable
fun StringField(modifier: Modifier = Modifier,
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
}

@Composable
fun TextItem(text: String, color: Color, fontSize: TextUnit, isLabel: Boolean = false) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        lineHeight = 24.sp,
        modifier = if (isLabel) Modifier.padding(bottom = 8.dp, start = 8.dp) else Modifier
    )
}

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    hint: String,
    text: MutableState<String>,
    isNumber: Boolean = false,
    isMoney: Boolean = false,
    isSaving: Boolean = false,
    isSignUp: Boolean = false,
    isSearch: Boolean = false,
    isPassword: Boolean = false,
    maxLength: Int = 0,
    onClick: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(if (isSearch) MainBgLightGray else MainWhite)
    ) {
        label?.let {
            TextItem(label, MainBlack, 16.sp, true)
        }

        BasicTextField(
            value = text.value,
            onValueChange = { newText ->
                if (maxLength == 0 || newText.length <= maxLength) {
                    if (isMoney) {
                        text.value = StringUtil.formatEditMoney(newText)
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
                imeAction = if (isSearch) ImeAction.Search else ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()

                    onClick?.invoke() // 검색
                }
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = if (isSearch) MainWhite else EditBgGray, shape = RoundedCornerShape(18.dp))
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

                        SubButton(
                            text = stringResource(R.string.email_duplicate_label),
                            onButtonClick = { /* TODO: 이메일 중복 확인 */ })
                    }

                    if (isMoney) {
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Text(
                            text = stringResource(R.string.money_label_default),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MainBlack
                        )
                    }

                    if (isSaving) {
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Text(
                            text = stringResource(R.string.saving_item_name_label_default),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MainBlack
                        )
                    }

                    if (isSearch) {
                        Spacer(modifier = Modifier.width(width = 8.dp))

                        Icon(
                            painter = painterResource(R.drawable.icon_search),
                            contentDescription = "",
                            tint = EditTextGray,
                            modifier = Modifier.clickable {
                                keyboardController?.hide()

                                onClick?.invoke() // 검색
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun CommonTextArea(
    description: MutableState<String>,
    placeholder: String? = null,
    charLimit: Int? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
       TextField(
            value = description.value,
            onValueChange = {
                if (charLimit == null || it.length <= charLimit) {
                    description.value = it
                }
            },
            placeholder = { Text(text = placeholder?:"", color = Color.Gray, fontSize = 20.sp, fontWeight = FontWeight.Medium) },
           textStyle = TextStyle(
               fontSize = 20.sp,
               fontWeight = FontWeight.Medium,
               color = MainBlack
           ),
           modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(MainBgLightGray, shape = RoundedCornerShape(18.dp)),
            shape = RoundedCornerShape(18.dp),
           colors = TextFieldDefaults.colors(
               focusedContainerColor = MainBgLightGray,
               unfocusedContainerColor = MainBgLightGray,
               disabledContainerColor = MainBgLightGray,
               errorContainerColor = MainBgLightGray,
               focusedIndicatorColor = Color.Transparent,
               unfocusedIndicatorColor = Color.Transparent,
               disabledIndicatorColor = Color.Transparent,
               errorIndicatorColor = Color.Transparent
           )
        )

        // 글자 수 제한이 있을 경우만 카운트 표시
        charLimit?.let {
            Text(
                text = "${description.value.length} / $charLimit",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
    ) {
//        val menuItems = mutableListOf("NH농협 312-0139-3754-31", "하나 312-0139-3754-31", "우리 312-0139-3754-31", "토스뱅크 312-0139-3754-31")
//        SelectAccountField(accounts = menuItems)

        val text1 = remember { mutableStateOf("") }
        EmailField(true, text1, onClick = {})

        val text2 = remember { mutableStateOf("") }
        PasswordField(stringResource(R.string.password_label), stringResource(R.string.password_hint), text2)

        val text3 = remember { mutableStateOf("") }
        NumberField(label = "금액", hint = "0", text = text3, isMoney = true)

        val text4 = remember { mutableStateOf("") }
        NumberField(label = "전화번호", hint = "전화번호 입력", text = text4)

        val text5 = remember { mutableStateOf("") }
        StringField(label = "적금", hint = "이찬원", text = text5, isSaving = true)

        val text6 = remember { mutableStateOf("") }
        StringField(label = "이름", hint = "이름 입력", text = text6)

        val text7 = remember { mutableStateOf("") }
        StringField(label = "응원메시지(20자)", hint = "메시지 입력", text = text7, maxLength = 20)

        TextItem("검색창", MainBlack, 16.sp)
        val text8 = remember { mutableStateOf("") }
        SearchField(text = text8, onClick = { /* 검색 */ })
    }
}