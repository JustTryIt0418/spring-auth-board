package com.example.authboard.domain.comment.db;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentEntity is a Querydsl query type for CommentEntity
 */
@SuppressWarnings("this-escape")
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentEntity extends EntityPathBase<CommentEntity> {

    private static final long serialVersionUID = -80973622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentEntity commentEntity = new QCommentEntity("commentEntity");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.OffsetDateTime> createdAt = createDateTime("createdAt", java.time.OffsetDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.authboard.domain.post.db.QPostEntity post;

    public final EnumPath<com.example.authboard.domain.comment.db.enums.CommentStatus> status = createEnum("status", com.example.authboard.domain.comment.db.enums.CommentStatus.class);

    public final DateTimePath<java.time.OffsetDateTime> updatedAt = createDateTime("updatedAt", java.time.OffsetDateTime.class);

    public final com.example.authboard.domain.user.db.QUserEntity user;

    public QCommentEntity(String variable) {
        this(CommentEntity.class, forVariable(variable), INITS);
    }

    public QCommentEntity(Path<? extends CommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentEntity(PathMetadata metadata, PathInits inits) {
        this(CommentEntity.class, metadata, inits);
    }

    public QCommentEntity(Class<? extends CommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.example.authboard.domain.post.db.QPostEntity(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.example.authboard.domain.user.db.QUserEntity(forProperty("user")) : null;
    }

}

