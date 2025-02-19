package prography.pingpong_game.room.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class UserNotInRoomException extends PingPongException {
	public UserNotInRoomException(ApiStatus apiStatus) {
		super(apiStatus);
	}
}
