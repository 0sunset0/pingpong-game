package prography.pingpong_game.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PingPongException extends RuntimeException {
    private final ErrorStatus errorStatus;
    public PingPongException(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }
}
