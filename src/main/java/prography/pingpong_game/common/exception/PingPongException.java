package prography.pingpong_game.common.exception;

import lombok.Getter;

@Getter
public class PingPongException extends RuntimeException {
    private final ApiStatus apiStatus;
    public PingPongException(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }
}
