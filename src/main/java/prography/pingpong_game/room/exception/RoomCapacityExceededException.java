package prography.pingpong_game.room.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class RoomCapacityExceededException extends PingPongException {
	public RoomCapacityExceededException(ApiStatus apiStatus) {
		super(apiStatus);
	}
}
