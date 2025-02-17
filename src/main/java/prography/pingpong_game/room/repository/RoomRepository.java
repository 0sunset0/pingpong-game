package prography.pingpong_game.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prography.pingpong_game.room.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {
}
