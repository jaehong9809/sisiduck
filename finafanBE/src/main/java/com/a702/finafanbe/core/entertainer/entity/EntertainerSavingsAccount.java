package com.a702.finafanbe.core.entertainer.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntertainerSavingsAccount extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "entertainer_id")
    private Long entertainerId;

    @Column(nullable = false, name="user_id")
    private Long userId;

    @Column(nullable = false, name ="account_name")
    private String accountName;

    @Column(nullable = false, name = "account_no")
    private String accountNo;

    public static EntertainerSavingsAccount of(
            Long entertainerId,
            String accountName,
            String accountNo
    ) {
        return new EntertainerSavingsAccount(
                entertainerId,
                accountName,
                accountNo
        );
    }

    private EntertainerSavingsAccount(
            Long entertainerId,
            String accountName,
            String accountNo
    ){
        this.entertainerId = entertainerId;
        this.accountName = accountName;
        this.accountNo = accountNo;
    }
}
//우리는 개인 적금이지만, 매일 출납이되야함.
//적금은 매 날짜마다 출금이 가능하도록 되어있음.
//기본적으로 출납을 하도록하되,
//이자율을 우리가 래핑해서 활용해야함.
//그럼 이자율에 따른 이자나 중도 해지에 대한 이자는. 우리가 자체적으로 제공해줘야함.
//그럼 우리 계좌에서 이체를 하는데...

//음.. 수시입출금이랑 연예인 적금이랑 동시에 만들고 사라지게 만들어야할 수 도 있겠는걸?