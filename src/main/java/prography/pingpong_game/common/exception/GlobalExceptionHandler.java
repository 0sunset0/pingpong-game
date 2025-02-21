package prography.pingpong_game.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
        log.warn("{}({}) - {}", e.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ApiStatus.SERVER_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        log.warn("{}({}) - {}", e.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ApiStatus.SERVER_ERROR));
    }
}
