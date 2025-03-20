package com.a702.finafanbe.core.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 930990939L;

    public static final QAccount account = new QAccount("account");

    public final com.a702.finafanbe.global.common.entity.QBaseEntity _super = new com.a702.finafanbe.global.common.entity.QBaseEntity(this);

    public final StringPath accountDescription = createString("accountDescription");

    public final DateTimePath<java.time.LocalDateTime> accountExpiryDate = createDateTime("accountExpiryDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> accountId = createNumber("accountId", Long.class);

    public final StringPath accountName = createString("accountName");

    public final StringPath accountNo = createString("accountNo");

    public final NumberPath<Integer> accountPw = createNumber("accountPw", Integer.class);

    public final StringPath accountTypeCode = createString("accountTypeCode");

    public final StringPath accountTypeUniqueNo = createString("accountTypeUniqueNo");

    public final NumberPath<java.math.BigDecimal> balance = createNumber("balance", java.math.BigDecimal.class);

    public final StringPath bankCode = createString("bankCode");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath currency = createString("currency");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final NumberPath<java.math.BigDecimal> dailyTransferLimit = createNumber("dailyTransferLimit", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> interestRate = createNumber("interestRate", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> lastTransactionDate = createDateTime("lastTransactionDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> maxSubscriptionBalance = createNumber("maxSubscriptionBalance", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> minSubscriptionBalance = createNumber("minSubscriptionBalance", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<java.math.BigDecimal> oneTimeTransferLimit = createNumber("oneTimeTransferLimit", java.math.BigDecimal.class);

    public final StringPath rateDescription = createString("rateDescription");

    public final StringPath status = createString("status");

    public final DateTimePath<java.time.LocalDateTime> subscriptionPeriod = createDateTime("subscriptionPeriod", java.time.LocalDateTime.class);

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

