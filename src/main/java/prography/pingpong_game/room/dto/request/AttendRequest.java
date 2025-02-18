package prography.pingpong_game.room.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record AttendRequest(
		@Schema(description = "참가하는 유저 ID", example = "1")
		Long userId
) {
}
