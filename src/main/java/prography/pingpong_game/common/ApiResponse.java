package prography.pingpong_game.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import static prography.pingpong_game.common.ErrorStatus.SUCCESS;

@Getter
public class ApiResponse<T> {
    @Schema(description = "응답 코드", example = "200")
    private Integer code;
    @Schema(description = "응답 메시지", example = "API 요청이 성공했습니다.")
    private String message;
    @Schema(description = "응답 결과 데이터", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T result;

    public static <T> ApiResponse<T> success() {
        return new ApiResponse(SUCCESS.getHttpStatus().value(), SUCCESS.getMessage());
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse(SUCCESS.getHttpStatus().value(), SUCCESS.getMessage(), result);
    }

    public static <T> ApiResponse<T> error(ErrorStatus constants) {
        return new ApiResponse<>(constants.getHttpStatus().value(), constants.getMessage());
    }

    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
