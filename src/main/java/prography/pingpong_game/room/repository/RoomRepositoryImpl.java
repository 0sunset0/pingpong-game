package prography.pingpong_game.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import prography.pingpong_game.room.entity.Room;

import java.util.List;
import java.util.Optional;

import static prography.pingpong_game.room.entity.QRoom.room;
import static prography.pingpong_game.room.entity.QRoomCapacity.roomCapacity;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom{
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Room> findAllRooms(PageRequest pageable) {
		List<Room> rooms = queryFactory
				.selectFrom(room)
				.orderBy(room.id.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		long totalElements = getTotalRoomsCount();
		return new PageImpl<>(rooms, pageable, totalElements);
	}

	@Override
	public Optional<Room> findRoomWithCapacity(Long roomId) {
		Room result = queryFactory
				.selectFrom(room)
				.join(room.roomCapacity, roomCapacity).fetchJoin()
				.where(room.id.eq(roomId))
				.fetchOne();
		return Optional.ofNullable(result);
	}

	private long getTotalRoomsCount() {
		return queryFactory
				.select(room.count())
				.from(room)
				.fetchOne();
	}
}
