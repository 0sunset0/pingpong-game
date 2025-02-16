package prography.pingpong_game.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prography.pingpong_game.room.entity.UserRoom;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {
    boolean existsByUserId(Long userId);
}
