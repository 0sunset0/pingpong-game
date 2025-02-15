package prography.pingpong_game.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import prography.pingpong_game.common.exception.ErrorStatus;

import java.util.Optional;

import static prography.pingpong_game.common.exception.ErrorStatus.SUCCESS;

public record ApiResponse<T>(
        @Schema(description = "응답 코드", example = "200") Integer code,
        @Schema(description = "응답 메시지", example = "API 요청이 성공했습니다.") String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) Optional<T> result) {
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(SUCCESS.getHttpStatus().value(), SUCCESS.getMessage(), Optional.empty());
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(SUCCESS.getHttpStatus().value(), SUCCESS.getMessage(), Optional.of(result));
    }

    public static <T> ApiResponse<T> error(ErrorStatus constants) {
        return new ApiResponse<>(constants.getHttpStatus().value(), constants.getMessage(), Optional.empty());
    }
}
