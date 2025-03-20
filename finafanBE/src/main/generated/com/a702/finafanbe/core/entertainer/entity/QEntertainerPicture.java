package com.a702.finafanbe.core.entertainer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntertainerPicture is a Querydsl query type for EntertainerPicture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntertainerPicture extends EntityPathBase<EntertainerPicture> {

    private static final long serialVersionUID = -1116218269L;

    public static final QEntertainerPicture entertainerPicture = new QEntertainerPicture("entertainerPicture");

    public final NumberPath<Long> entertainerId = createNumber("entertainerId", Long.class);

    public final NumberPath<Long> entertainerPictureId = createNumber("entertainerPictureId", Long.class);

    public final StringPath entertainerPictureUrl = createString("entertainerPictureUrl");

    public QEntertainerPicture(String variable) {
        super(EntertainerPicture.class, forVariable(variable));
    }

    public QEntertainerPicture(Path<? extends EntertainerPicture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntertainerPicture(PathMetadata metadata) {
        super(EntertainerPicture.class, metadata);
    }

}

