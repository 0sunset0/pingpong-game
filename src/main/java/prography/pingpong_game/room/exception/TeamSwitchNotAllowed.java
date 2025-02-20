package prography.pingpong_game.room.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class TeamSwitchNotAllowed extends PingPongException {
	public TeamSwitchNotAllowed(ApiStatus apiStatus) {
		super(apiStatus);
	}
}
