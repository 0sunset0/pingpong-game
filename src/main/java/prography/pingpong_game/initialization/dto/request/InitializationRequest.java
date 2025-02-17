package prography.pingpong_game.initialization.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "초기화 요청 DTO")
public record InitializationRequest(
        @Schema(description = "랜덤 데이터 생성 시드 값", example = "42")
        Integer seed,
        @Schema(description = "생성할 데이터 개수", example = "10")
        Integer quantity) {
}
