package prography.pingpong_game.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import prography.pingpong_game.room.entity.Room;

import java.util.List;

import static prography.pingpong_game.room.entity.QRoom.room;

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

	private long getTotalRoomsCount() {
		return queryFactory
				.select(room.count())
				.from(room)
				.fetchOne();
	}
}
