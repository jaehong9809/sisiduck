package com.a702.finafan.presentation.navigation

sealed class NavRoutes(val route: String) {
    object Main : NavRoutes("main")

    /* Login */
    object Login : NavRoutes("login")

    /* Chat */
    object Chat : NavRoutes("chat")

    /* Ble */
    object Ble : NavRoutes("ble")

    /* Saving */
    object Saving : NavRoutes("saving")
    object SavingDeposit : NavRoutes("saving_deposit")

    object SavingMain : NavRoutes("saving_main")
    object TransactionDetail : NavRoutes("transaction_detail")
    object SavingAccountManage : NavRoutes("saving_account_manage")
    object SavingCancel : NavRoutes("saving_cancel")

    object SavingDesc : NavRoutes("saving_desc")
    object TermGuide : NavRoutes("term_guide")
    object SavingNameInput : NavRoutes("saving_name_input")
    object SavingSelectAccount : NavRoutes("saving_select_account")

    object StarSearch : NavRoutes("star_search")
    object StarSelect : NavRoutes("star_select")

    /* ConnectAccount */
    object Account : NavRoutes("account")
    object AccountInput : NavRoutes("account_input")
    object AccountSend : NavRoutes("account_send")
    object AccountCode : NavRoutes("account_code")
    object AccountCodeConfirm : NavRoutes("account_code_confirm")
    object ConnectAccount : NavRoutes("connect_account")

    object ConnectBank : NavRoutes("connect_bank?from={from}") {
        fun from(from: String) = "connect_bank?from=$from"
    }

    object SelectAccount : NavRoutes("select_account?from={from}") {
        fun from(from: String) = "select_account?from=$from"
    }

    /* Ranking */
    object RankingMain : NavRoutes("ranking_main")
    object RankingDetail : NavRoutes("ranking_detail")

    /* Funding */
    object Funding : NavRoutes("funding")
    object FundingMain : NavRoutes("funding_main")
    object FundingDetail : NavRoutes("funding_detail/{fundingId}") {
        fun withId(fundingId: Long) = "funding_detail/$fundingId"
    }
    object FundingJoin : NavRoutes("funding_join/{fundingId}") {
        fun withId(fundingId: Long) = "funding_join/$fundingId"
    }
    object FundingCreate : NavRoutes("funding_create")
    object FundingDeposit : NavRoutes("funding_deposit")
    object FundingWithdraw : NavRoutes("funding_withdraw")
    object FundingCancel : NavRoutes("funding_cancel")

    /* AllAccount */
    object AllAccount : NavRoutes("all_account")
    object AllAccountParam : NavRoutes("all_account?selectedTabIndex={selectedTabIndex}")
}