package prography.pingpong_game.room.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class RoomNotExitException extends PingPongException {

	public RoomNotExitException(ApiStatus apiStatus) {
		super(apiStatus);
	}
}
