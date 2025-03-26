package com.a702.finafanbe.core.savings.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "savings_type")
public abstract class SavingsAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsAccountId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "savings_item_id")
    private String savingsItemId;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "account_nickname")
    private String accountNickname;

    private Long balance;

    @Column(name = "account_expiry_date")
    private LocalDateTime accountExpiryDate;

    protected SavingsAccount(Long userId, String savingsItemId, String accountNo, String accountNickname, Long balance, LocalDateTime accountExpiryDate) {
        this.userId = userId;
        this.savingsItemId = savingsItemId;
        this.accountNo = accountNo;
        this.accountNickname = accountNickname;
        this.balance = balance;
        this.accountExpiryDate = accountExpiryDate;
    }

    //        private Long userId;
//        private Long savingsItemId;
//        private String accountNo
//        private String accountNickname;
//        private Long balance;
//        private LocalDateTime accountExpiryDate;`
}
