package com.a702.finafan.presentation.account

import com.a702.finafan.R

enum class BankEnum(val code: String, val drawableRes: Int) {
    KOREA("001", R.drawable.icon_bok_bank),
    KDB("002", R.drawable.icon_kdb_bank),
    IBK("003", R.drawable.icon_ibk_bank),
    KB("004", R.drawable.icon_kb_bank),
    NH("011", R.drawable.icon_nh_bank),
    WOORI("020", R.drawable.icon_woori_bank),
    SC("023", R.drawable.icon_sc_bank),
    CITI("027", R.drawable.icon_city_bank),
    DAEGU("032", R.drawable.icon_dgb_bank),
    GWANGJU("034", R.drawable.icon_kj_bank),
    JEJU("035", R.drawable.icon_jj_bank),
    JEONBUK("037", R.drawable.icon_jj_bank),
    GYEONGNAM("039", R.drawable.icon_bnk_bank),
    SMG("045", R.drawable.icon_mg_bank),
    HANA("081", R.drawable.icon_hana_bank),
    SHINHAN("088", R.drawable.icon_shinhan_bank),
    KAKAO("090", R.drawable.icon_kakao_bank),
    SSAFY("999", R.drawable.icon_ssafy_bank);

    companion object {
        // 은행 코드로 Enum 찾기
        fun fromCode(code: String): BankEnum? = BankEnum.entries.find { it.code == code }

        // 은행 코드로 Drawable 가져오기
        fun getDrawable(code: String): Int = fromCode(code)?.drawableRes ?: R.drawable.icon_ssafy_bank
    }
}
