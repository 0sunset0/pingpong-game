package prography.pingpong_game.room.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record StartGameRequest(
		@Schema(description = "호스트 유저 ID", example = "1")
		Long userId
) {
}
