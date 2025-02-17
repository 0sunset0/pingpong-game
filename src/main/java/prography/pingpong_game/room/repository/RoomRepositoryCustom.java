package prography.pingpong_game.room.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import prography.pingpong_game.room.entity.Room;

public interface RoomRepositoryCustom {
	Page<Room> findAllRooms(PageRequest pageable);
}
