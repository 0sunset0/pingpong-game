package prography.pingpong_game.room.repository;

import prography.pingpong_game.room.entity.UserRoom;

import java.util.Optional;

public interface UserRoomRepositoryCustom {
	boolean existsRoomByUserId(Long userId);

	Optional<UserRoom> findUserRoom(Long userId, Long roomId);

	void deleteUserRoom(Long userId, Long roomId);
}
