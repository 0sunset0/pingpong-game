package prography.pingpong_game.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.entity.Team;
import prography.pingpong_game.room.exception.*;


@Service
@RequiredArgsConstructor
public class RoomValidator {

	void validateCanExitRoom(Room room) {
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

	public void validateCanSwitchTeam(Room room, Team team) {
		boolean canSwitchTeam = room.getRoomCapacity().canSwitchTeam(team);
		if (!canSwitchTeam) {
			throw new TeamSwitchNotAllowed(ApiStatus.BAD_REQUEST);
		}
	}
}
