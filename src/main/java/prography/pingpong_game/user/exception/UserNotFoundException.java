package prography.pingpong_game.user.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class UserNotFoundException extends PingPongException {
    public UserNotFoundException(ApiStatus apiStatus) {
        super(apiStatus);
    }
}
