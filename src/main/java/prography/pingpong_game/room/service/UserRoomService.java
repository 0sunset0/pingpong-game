package prography.pingpong_game.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.entity.Team;
import prography.pingpong_game.room.entity.UserRoom;
import prography.pingpong_game.room.exception.UserNotInRoomException;
import prography.pingpong_game.room.repository.UserRoomRepository;
import prography.pingpong_game.user.entity.User;

@Service
@RequiredArgsConstructor
public class UserRoomService {
	private final UserRoomRepository userRoomRepository;

	@Transactional(readOnly = true)
	public UserRoom findUserRoom(Long roomId, Long userId) {
		UserRoom userRoom = userRoomRepository.findUserRoom(userId, roomId)
			.orElseThrow(() -> new UserNotInRoomException(ApiStatus.BAD_REQUEST));
		return userRoom;
	}

	@Transactional
	public void saveUserRoom(Room room, User user, Team team) {
		UserRoom userRoom = UserRoom.create(room, user, team);
		userRoomRepository.save(userRoom);
	}

	public void deleteAllUserRooms(Long roomId) {
		userRoomRepository.deleteAllByRoomId(roomId);
	}

	public void deleteUser(UserRoom userRoom) {
		userRoomRepository.delete(userRoom);
	}
}
