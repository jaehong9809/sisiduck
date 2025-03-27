package com.a702.finafan.presentation.navigation

sealed class NavRoutes(val route: String) {
    object Main : NavRoutes("main")

    /* Chat */
    object Chat : NavRoutes("chat")

    /* Saving */
    object Saving : NavRoutes("saving")
    object SavingDeposit : NavRoutes("saving_deposit")

    object SavingMain : NavRoutes("saving_main")
    object TransactionDetail : NavRoutes("transaction_detail")
    object SavingAccountInfo : NavRoutes("saving_account_info")
    object SavingCancel : NavRoutes("saving_cancel")

    object SavingDesc : NavRoutes("saving_desc")
    object TermGuide : NavRoutes("term_guide")
    object SavingNameInput : NavRoutes("saving_name_input")
    object SavingSelectAccount : NavRoutes("saving_select_account")

    object StarSearch : NavRoutes("star_search")
    object StarSelect : NavRoutes("star_select")

    /* ConnectAccount */
    object Account : NavRoutes("account")
    object ConnectBank : NavRoutes("connect_bank")
    object AccountInput : NavRoutes("account_input")
    object AccountSend : NavRoutes("account_send")
    object AccountCode : NavRoutes("account_code")
    object AccountCodeConfirm : NavRoutes("account_code_confirm")
    object ConnectAccount : NavRoutes("connect_account")

    /* Funding */
    object Funding : NavRoutes("funding")
    object FundingMain : NavRoutes("funding_main")
    object FundingDetail : NavRoutes("funding_detail")
    object FundingJoin : NavRoutes("funding_join")
}