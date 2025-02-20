package prography.pingpong_game.room.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record TeamSwitchRequest(
		@Schema(description = "팀을 바꾸려는 유저 ID", example = "1")
		Long userId
) {
}
