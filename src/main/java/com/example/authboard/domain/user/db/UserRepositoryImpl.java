package com.example.authboard.domain.user.db;

import com.example.authboard.domain.user.controller.model.UserListRequest;
import com.example.authboard.domain.user.controller.model.UserResponse;
import com.example.authboard.domain.user.db.enums.UserRole;
import com.example.authboard.domain.user.db.enums.UserStatus;
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
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QUserEntity user = QUserEntity.userEntity;

    @Override
    public Page<UserResponse> findAllUsers(UserListRequest request, Pageable pageable) {

        BooleanBuilder whereCondition = buildWhereCondition(request);

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(pageable);

        long total = Optional.ofNullable(
                jpaQueryFactory.select(user.countDistinct())
                        .from(user)
                        .where(whereCondition)
                        .fetchOne()
        ).orElse(0L);

        List<UserResponse> content = Optional.ofNullable(
                jpaQueryFactory.select(Projections.fields(UserResponse.class,
                        user.id,
                        user.email,
                        user.nickname,
                        user.role,
                        user.status,
                        user.createdAt,
                        user.updatedAt,
                        user.lastLoginAt
                ))
                .from(user)
                .where(whereCondition)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
        ).orElse(List.of());

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanBuilder buildWhereCondition(UserListRequest request) {
        BooleanBuilder whereCondition = new BooleanBuilder();

        String searchType = request.getSearchType();
        String searchKeyword = request.getSearchKeyword();

        UserRole userRole = UserRole.ROLE_USER;
        UserStatus status = UserStatus.REGISTERED;

        if (StringUtils.isBlank(searchType) || StringUtils.isBlank(searchKeyword)) {
            return whereCondition;
        }

        if (searchType.equalsIgnoreCase("nickname")) {
            whereCondition.and(user.nickname.containsIgnoreCase(searchKeyword));
        } else if (searchType.equalsIgnoreCase("email")) {
            whereCondition.and(user.email.containsIgnoreCase(searchKeyword));
        } else if (searchType.equalsIgnoreCase("role")
                && Objects.equals(searchKeyword.toUpperCase(), "ROLE_ADMIN")) {
                userRole = UserRole.ROLE_ADMIN;
        } else if (searchType.equalsIgnoreCase("status")
                && Objects.equals(searchKeyword.toUpperCase(), "UNREGISTERED")) {
                status = UserStatus.UNREGISTERED;
        }

        whereCondition.and(user.role.eq(userRole));
        whereCondition.and(user.status.eq(status));

        return whereCondition;
    }

    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable) {
        return pageable.getSort().stream()
                .findFirst()
                .map(order ->
                        switch (order.getProperty()) {
                            case "id" -> order.isAscending() ? user.id.asc() : user.id.desc();
                            case "email" -> order.isAscending() ? user.email.asc() : user.email.desc();
                            case "nickname" -> order.isAscending() ? user.nickname.asc() : user.nickname.desc();
                            case "lastLoginAt" -> order.isAscending() ? user.lastLoginAt.asc() : user.lastLoginAt.desc();
                            default -> user.id.desc();
                        }
                )
                .orElse(user.id.desc());
    }
}
