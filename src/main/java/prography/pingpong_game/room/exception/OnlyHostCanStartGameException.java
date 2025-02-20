package prography.pingpong_game.room.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class OnlyHostCanStartGameException extends PingPongException {
	public OnlyHostCanStartGameException(ApiStatus apiStatus) {
		super(apiStatus);
	}
}
