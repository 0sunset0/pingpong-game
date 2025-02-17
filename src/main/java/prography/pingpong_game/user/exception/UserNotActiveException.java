package prography.pingpong_game.user.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class UserNotActiveException extends PingPongException {
    public UserNotActiveException(ApiStatus apiStatus) {
        super(apiStatus);
    }
}
