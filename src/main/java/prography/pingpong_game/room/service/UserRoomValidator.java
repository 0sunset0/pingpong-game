package prography.pingpong_game.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.exception.UserAlreadyInRoomException;
import prography.pingpong_game.room.repository.UserRoomRepository;

@Service
@RequiredArgsConstructor
public class UserRoomValidator {
	private final UserRoomRepository userRoomRepository;

	void validateUserNotInAnyRoom(Long userId) {
		boolean hasExistingRoom = userRoomRepository.existsRoomByUserId(userId);
		if (hasExistingRoom) {
			throw new UserAlreadyInRoomException(ApiStatus.BAD_REQUEST);
		}
	}
}
