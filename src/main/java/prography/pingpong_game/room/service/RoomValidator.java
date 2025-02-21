package prography.pingpong_game.room.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.entity.Team;
import prography.pingpong_game.room.exception.OnlyHostCanStartGameException;
import prography.pingpong_game.room.exception.RoomIsFullException;
import prography.pingpong_game.room.exception.RoomIsNotFullException;
import prography.pingpong_game.room.exception.RoomNotExitException;
import prography.pingpong_game.room.exception.RoomNotWaitingException;
import prography.pingpong_game.room.exception.TeamSwitchNotAllowed;

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
			throw new RoomIsFullException(ApiStatus.BAD_REQUEST);
		}
	}

	void validateRoomIsFull(Room room) {
		if (!room.isFull()) {
			throw new RoomIsNotFullException(ApiStatus.BAD_REQUEST);
		}
	}

	void validateHost(Room room, Long userId) {
		if (!room.isHost(userId)) {
			throw new OnlyHostCanStartGameException(ApiStatus.BAD_REQUEST);
		}
	}

	void validateRoomWaiting(Room room) {
		if (!room.isWaiting()) {
			throw new RoomNotWaitingException(ApiStatus.BAD_REQUEST);
		}
	}

	void validateCanSwitchTeam(Room room, Team team) {
		boolean canSwitchTeam = room.canSwitchTeam(team);
		if (!canSwitchTeam) {
			throw new TeamSwitchNotAllowed(ApiStatus.BAD_REQUEST);
		}
	}
}
