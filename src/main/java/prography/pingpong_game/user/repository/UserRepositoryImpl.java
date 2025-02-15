package prography.pingpong_game.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import prography.pingpong_game.user.entity.User;

import java.util.List;

import static prography.pingpong_game.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        List<User> users = queryFactory
                .selectFrom(user)
                .orderBy(user.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long totalElements = getTotalUsersCount();
        return new PageImpl<>(users, pageable, totalElements);
    }

    //SELECT COUNT(*) FROM users;
    private long getTotalUsersCount() {
        return queryFactory
                .select(user.count())
                .from(user)
                .fetchOne();
    }
}
