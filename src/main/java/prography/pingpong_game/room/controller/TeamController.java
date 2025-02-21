package prography.pingpong_game.room.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prography.pingpong_game.common.dto.ApiResponse;
import prography.pingpong_game.room.dto.request.TeamSwitchRequest;
import prography.pingpong_game.room.service.RoomService;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
	private final RoomService roomService;
	@Operation(summary = "팀 변경 API", description = "팀을 변경합니다. ")
	@PutMapping("/{roomId}")
	public ApiResponse switchTeam(
			@Parameter(description = "참여하고 있는 방 ID", example = "1")
			@PathVariable Long roomId,
			@RequestBody TeamSwitchRequest teamSwitchRequest) {
		roomService.switchTeam(roomId, teamSwitchRequest);
		return ApiResponse.success();
	}
}
