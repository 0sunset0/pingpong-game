package prography.pingpong_game.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import prography.pingpong_game.room.entity.UserRoomStatus;

import static prography.pingpong_game.room.entity.QUserRoom.userRoom;

@Repository
@RequiredArgsConstructor
public class UserRoomRepositoryImpl implements UserRoomRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public boolean existsActiveRoomByUserId(Long userId) {
		Integer fetchOne = queryFactory
				.selectOne()
				.from(userRoom)
				.where(
						userRoom.user.id.eq(userId),
						userRoom.status.eq(UserRoomStatus.ACTIVE)
				)
				.fetchFirst();
		return fetchOne != null;
	}
}
