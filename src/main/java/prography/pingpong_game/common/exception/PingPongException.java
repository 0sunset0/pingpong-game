package prography.pingpong_game.common.exception;

import lombok.Getter;

@Getter
public class PingPongException extends RuntimeException {
    private final ErrorStatus errorStatus;
    public PingPongException(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }
}
