package prography.pingpong_game;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static prography.pingpong_game.ResponseConstants.SUCCESS;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T result;

    public static <T> ApiResponse<T> success() {
        return new ApiResponse(SUCCESS.getHttpStatus().value(), SUCCESS.getMessage());
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse(SUCCESS.getHttpStatus().value(), SUCCESS.getMessage(), result);
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
