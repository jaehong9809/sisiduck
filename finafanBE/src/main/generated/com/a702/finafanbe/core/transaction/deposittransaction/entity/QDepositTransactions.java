package com.a702.finafanbe.core.transaction.deposittransaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDepositTransactions is a Querydsl query type for DepositTransactions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDepositTransactions extends EntityPathBase<DepositTransactions> {

    private static final long serialVersionUID = 1195507982L;

    public static final QDepositTransactions depositTransactions = new QDepositTransactions("depositTransactions");

    public final StringPath depositAccountNo = createString("depositAccountNo");

    public final StringPath depositTransactionSummary = createString("depositTransactionSummary");

    public final StringPath status = createString("status");

    public final NumberPath<Long> transactionBalance = createNumber("transactionBalance", Long.class);

    public final DateTimePath<java.time.LocalDateTime> transactionDate = createDateTime("transactionDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> transactionId = createNumber("transactionId", Long.class);

    public final StringPath transactionType = createString("transactionType");

    public final StringPath transactionTypeName = createString("transactionTypeName");

    public final NumberPath<Long> transactionUniqueNo = createNumber("transactionUniqueNo", Long.class);

    public final StringPath withdrawalAccountNo = createString("withdrawalAccountNo");

    public final StringPath withdrawalTransactionSummary = createString("withdrawalTransactionSummary");

    public QDepositTransactions(String variable) {
        super(DepositTransactions.class, forVariable(variable));
    }

    public QDepositTransactions(Path<? extends DepositTransactions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepositTransactions(PathMetadata metadata) {
        super(DepositTransactions.class, metadata);
    }

}

