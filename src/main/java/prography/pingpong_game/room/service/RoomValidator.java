package prography.pingpong_game.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.exception.*;
import prography.pingpong_game.room.repository.UserRoomRepository;


@Service
@RequiredArgsConstructor
public class RoomValidator {
	private final UserRoomRepository userRoomRepository;

	void validateUserNotInAnyRoom(Long userId) {
		boolean hasExistingRoom = userRoomRepository.existsRoomByUserId(userId);
		if (hasExistingRoom) {
			throw new UserAlreadyInRoomException(ApiStatus.BAD_REQUEST);
		}
	}

	void validateRoomCanExit(Room room) {
		if (room.isExitNotAllowed()) {
			throw new RoomNotExitException(ApiStatus.BAD_REQUEST);
		}
	}

	void validateRoomNotFull(Room room) {
		if (room.isFull()) {
			throw new RoomCapacityExceededException(ApiStatus.BAD_REQUEST);
		}
	}

	void validateRoomWaiting(Room room) {
		if (!room.isWaiting()) {
			throw new RoomNotWaitingException(ApiStatus.BAD_REQUEST);
		}
	}

	void validateUserInRoom(Long userId, Long roomId) {
		boolean isExistUserRoom = userRoomRepository.findUserRoom(userId, roomId).isPresent();
		if (!isExistUserRoom) {
			throw new UserNotInRoomException(ApiStatus.BAD_REQUEST);
		}
	}
}
