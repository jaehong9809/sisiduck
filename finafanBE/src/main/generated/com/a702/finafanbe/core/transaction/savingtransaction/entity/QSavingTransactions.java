package com.a702.finafanbe.core.transaction.savingtransaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSavingTransactions is a Querydsl query type for SavingTransactions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSavingTransactions extends EntityPathBase<SavingTransactions> {

    private static final long serialVersionUID = -1481607896L;

    public static final QSavingTransactions savingTransactions = new QSavingTransactions("savingTransactions");

    public final StringPath depositAccountNo = createString("depositAccountNo");

    public final StringPath depositTransactionSummary = createString("depositTransactionSummary");

    public final NumberPath<Long> savingCount = createNumber("savingCount", Long.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> transactionBalance = createNumber("transactionBalance", Long.class);

    public final DateTimePath<java.time.LocalDateTime> transactionDate = createDateTime("transactionDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> transactionId = createNumber("transactionId", Long.class);

    public final StringPath transactionType = createString("transactionType");

    public final StringPath transactionTypeName = createString("transactionTypeName");

    public final NumberPath<Long> transactionUniqueNo = createNumber("transactionUniqueNo", Long.class);

    public final StringPath withdrawalAccountNo = createString("withdrawalAccountNo");

    public final StringPath withdrawalTransactionSummary = createString("withdrawalTransactionSummary");

    public QSavingTransactions(String variable) {
        super(SavingTransactions.class, forVariable(variable));
    }

    public QSavingTransactions(Path<? extends SavingTransactions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSavingTransactions(PathMetadata metadata) {
        super(SavingTransactions.class, metadata);
    }

}

