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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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

// 출금 계좌 선택 박스
@Composable
fun SelectAccountField() {
    var expandStatus by remember { mutableStateOf(false) }
    val menuItems = listOf("NH농협 312-0139-3754-31", "하나 312-0139-3754-31", "우리 312-0139-3754-31", "토스뱅크 312-0139-3754-31")

    var selectedAccount by remember { mutableStateOf(menuItems[0]) }

    TextItem("출금계좌 선택", MainBlack, 16.sp, true)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
            .padding(all = 16.dp)
            .clickable {
                expandStatus = true
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextItem(selectedAccount, MainBlack, 20.sp)
            Spacer(modifier = Modifier.width(width = 8.dp))
            Icon(
                painter = painterResource(R.drawable.angle_down),
                contentDescription = "",
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(MainWhite, shape = RoundedCornerShape(18.dp))
                .border(1.dp, MainBlack, shape = RoundedCornerShape(18.dp))
                .fillMaxWidth(),
            expanded = expandStatus,
            onDismissRequest = {
                expandStatus = false
            }
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item, fontSize = 20.sp, color = MainBlack, fontWeight = FontWeight.Normal) },
                    onClick = {
                        selectedAccount = item
                        expandStatus = false
                        println("Selected: $item")
                    }
                )
            }
        }
    }
}

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
                        val cleanedText = newText.replace(Regex("[^0-9]"), "")
                        text.value = StringUtil.formatEditMoney(cleanedText.toLong())
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
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MainBlack
                        )
                    }

                    if (isSaving) {
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Text(
                            text = "적금",
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

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
    ) {
        SelectAccountField()

        val text1 = remember { mutableStateOf("") }
        EmailField(true, text1, onClick = {})

        val text2 = remember { mutableStateOf("") }
        PasswordField("비밀번호", "비밀번호 입력", text2)

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