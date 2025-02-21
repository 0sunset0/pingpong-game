package prography.pingpong_game.room.repository;

import static prography.pingpong_game.room.entity.QUserRoom.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import prography.pingpong_game.room.entity.UserRoom;

@Repository
@RequiredArgsConstructor
public class UserRoomRepositoryImpl implements UserRoomRepositoryCustom {
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
	public void deleteAllByRoomId(Long roomId) {
		queryFactory.delete(userRoom)
			.where(userRoom.room.id.eq(roomId))
			.execute();
	}
}
