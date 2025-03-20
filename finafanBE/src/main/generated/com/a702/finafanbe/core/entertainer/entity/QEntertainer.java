package com.a702.finafanbe.core.entertainer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntertainer is a Querydsl query type for Entertainer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntertainer extends EntityPathBase<Entertainer> {

    private static final long serialVersionUID = 730485787L;

    public static final QEntertainer entertainer = new QEntertainer("entertainer");

    public final NumberPath<Integer> entertainerAge = createNumber("entertainerAge", Integer.class);

    public final NumberPath<Long> entertainerId = createNumber("entertainerId", Long.class);

    public final StringPath entertainerName = createString("entertainerName");

    public final StringPath entertainerProfileUrl = createString("entertainerProfileUrl");

    public final StringPath fandomName = createString("fandomName");

    public QEntertainer(String variable) {
        super(Entertainer.class, forVariable(variable));
    }

    public QEntertainer(Path<? extends Entertainer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntertainer(PathMetadata metadata) {
        super(Entertainer.class, metadata);
    }

}

