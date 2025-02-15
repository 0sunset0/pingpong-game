package prography.pingpong_game.room.exception;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.common.exception.PingPongException;

public class RoomNotFoundException extends PingPongException {
    public RoomNotFoundException(ApiStatus apiStatus) {
        super(apiStatus);
    }
}
