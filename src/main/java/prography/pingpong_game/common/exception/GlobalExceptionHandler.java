package prography.pingpong_game.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prography.pingpong_game.common.dto.ApiResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(PingPongException.class)
    public ApiResponse handlePingPongException(PingPongException e) {
        log.warn("{}({}) - {}", e.getClass().getSimpleName(), e.getApiStatus().getHttpStatus(), e.getApiStatus().getMessage());
        return ApiResponse.error(e.getApiStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse handleRuntimeException(RuntimeException e) {
        log.warn("{}({}) - {}", e.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ApiResponse.error(ApiStatus.SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        log.warn("{}({}) - {}", e.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ApiResponse.error(ApiStatus.SERVER_ERROR);
    }
}
