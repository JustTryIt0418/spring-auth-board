package com.example.authboard.domain.post.db;

import com.example.authboard.domain.post.controller.model.PostListRequest;
import com.example.authboard.domain.post.controller.model.PostResponse;
import com.example.authboard.domain.post.db.enums.PostStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QPostEntity post = QPostEntity.postEntity;

    @Override
    public Page<PostResponse> findAllPosts(PostListRequest request, Pageable pageable) {

        BooleanBuilder whereCondition = buildWhereCondition(request);

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(pageable);

        long total = Optional.ofNullable(
                jpaQueryFactory.select(post.countDistinct())
                        .from(post)
                        .where(whereCondition)
                        .fetchOne()
        ).orElse(0L);

        List<PostResponse> content = Optional.ofNullable(
                jpaQueryFactory.select(Projections.fields(PostResponse.class,
                                post.id,
                                post.title,
                                post.content,
                                post.createdAt,
                                post.updatedAt,
                                post.userId,
                                post.status
                        ))
                        .from(post)
                        .where(whereCondition)
                        .orderBy(orderSpecifier)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch()
        ).orElse(List.of());

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanBuilder buildWhereCondition(PostListRequest request) {
        BooleanBuilder whereCondition = new BooleanBuilder();
        whereCondition.and(post.status.eq(PostStatus.ACTIVE));

        String searchType = request.getSearchType();
        String searchKeyword = request.getSearchKeyword();

        if (StringUtils.isBlank(searchType) || StringUtils.isBlank(searchKeyword)) {
            return whereCondition;
        }

        if (searchType.equalsIgnoreCase("title")) {
            whereCondition.and(post.title.containsIgnoreCase(searchKeyword));
        } else if (searchType.equalsIgnoreCase("content")) {
            whereCondition.and(post.content.containsIgnoreCase(searchKeyword));
        }

        return whereCondition;
    }

    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable) {
        return pageable.getSort().stream()
                .findFirst()
                .map(order ->
                    switch (order.getProperty()) {
                        case "title" -> order.isAscending() ? post.title.asc() : post.title.desc();
                        case "id" -> order.isAscending() ? post.id.asc() : post.id.desc();
                        default -> post.id.desc();
                    }
                )
                .orElse(post.id.desc());
    }
}
