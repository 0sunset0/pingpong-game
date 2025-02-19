package prography.pingpong_game.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import prography.pingpong_game.room.entity.UserRoom;

import java.util.Optional;

import static prography.pingpong_game.room.entity.QUserRoom.userRoom;

@Repository
@RequiredArgsConstructor
public class UserRoomRepositoryImpl implements UserRoomRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	@Override
	public boolean existsRoomByUserId(Long userId) {
		Integer fetchOne = queryFactory
				.selectOne()
				.from(userRoom)
				.where(
						userRoom.user.id.eq(userId)
				)
				.fetchFirst();
		return fetchOne != null;
	}

	@Override
	public Optional<UserRoom> findUserRoom(Long userId, Long roomId) {
		UserRoom result = queryFactory
				.selectFrom(userRoom)
				.where(
						userRoom.user.id.eq(userId),
						userRoom.room.id.eq(roomId)
				)
				.fetchOne();
		return Optional.ofNullable(result);
	}

	@Override
	public void deleteUserRoom(Long userId, Long roomId) {
		queryFactory.delete(userRoom)
				.where(
						userRoom.user.id.eq(userId),
						userRoom.room.id.eq(roomId)
				)
				.execute();
	}
}
