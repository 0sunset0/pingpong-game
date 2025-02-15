package prography.pingpong_game.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(PingPongException.class)
    public ApiResponse handlePingPongException(PingPongException e) {
        log.warn("{}({}) - {}", e.getClass().getSimpleName(), e.getErrorStatus().getHttpStatus(), e.getErrorStatus().getMessage());
        return ApiResponse.error(e.getErrorStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse handleRuntimeException(RuntimeException e) {
        log.warn("{}({}) - {}", e.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ApiResponse.error(ErrorStatus.SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        log.warn("{}({}) - {}", e.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ApiResponse.error(ErrorStatus.SERVER_ERROR);
    }
}
