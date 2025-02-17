package prography.pingpong_game.room.repository;

public interface UserRoomRepositoryCustom {
	boolean existsActiveRoomByUserId(Long userId);
}
