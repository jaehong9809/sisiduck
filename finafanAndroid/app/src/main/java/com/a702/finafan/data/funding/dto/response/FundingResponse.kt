package com.a702.finafan.data.funding.dto.response

import com.a702.finafan.domain.funding.model.Funding
import java.time.LocalDate

data class FundingResponse(
    val entertainer: EntertainerResponse,
    val fundingId: Long,
    val fundingName: String,
    val fundingAccountNo: String,
    val status: String,
    val currentAmount: Long,
    val goalAmount: Long,
    val fundingExpiryDate: String
)

fun FundingResponse.toDomain(): Funding {
    return Funding(
        star = this.entertainer.toDomain(),
        id = this.fundingId,
        title = this.fundingName,
        accountNo = this.fundingAccountNo,
        status = this.status,
        currentAmount = this.currentAmount,
        goalAmount = this.goalAmount,
        fundingExpiryDate = LocalDate.parse(this.fundingExpiryDate)
    )

}

//fun Funding.toSavingAccount(): SavingAccount {
//    return SavingAccount(
//        accountId = this.id,
//        accountNo = this.accountNo,
//        accountName = this.title,
//        amount = this.currentAmount,
//        createdDate = "",
//        interestRate = "",
//        duration = 0,
//        imageUrl = "",
//        withdrawalAccount = Account()
//    )
//}