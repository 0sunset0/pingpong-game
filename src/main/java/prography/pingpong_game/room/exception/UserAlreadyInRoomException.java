package prography.pingpong_game.room.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class UserAlreadyInRoomException extends PingPongException {

    public UserAlreadyInRoomException(ApiStatus apiStatus) {
        super(apiStatus);
    }
}
