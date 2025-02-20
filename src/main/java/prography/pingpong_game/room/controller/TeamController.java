package prography.pingpong_game.room.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prography.pingpong_game.common.ApiResponse;
import prography.pingpong_game.room.dto.request.TeamSwitchRequest;
import prography.pingpong_game.room.service.RoomService;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
	private final RoomService roomService;

	@PutMapping("/{roomId}")
	public ApiResponse switchTeam(@PathVariable Long roomId, @RequestBody TeamSwitchRequest teamSwitchRequest) {
		roomService.switchTeam(roomId, teamSwitchRequest);
		return ApiResponse.success();
	}
}
