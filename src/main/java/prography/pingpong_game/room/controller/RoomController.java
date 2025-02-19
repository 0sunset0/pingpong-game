package prography.pingpong_game.room.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prography.pingpong_game.common.ApiResponse;
import prography.pingpong_game.room.dto.request.AttendRequest;
import prography.pingpong_game.room.dto.request.OutRoomRequest;
import prography.pingpong_game.room.dto.request.RoomCreateRequest;
import prography.pingpong_game.room.service.RoomService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    @Operation(summary = "방 생성 API", description = "방을 생성합니다.")
    @PostMapping
    public ApiResponse createRoom(@RequestBody RoomCreateRequest roomCreateRequest) {
        roomService.createRoom(roomCreateRequest);
        return ApiResponse.success();
    }
    @Operation(summary = "방 전체 조회 API", description = "방에 대한 전체 조회를 합니다.")
    @GetMapping()
    public ApiResponse findAllRooms(
            @RequestParam(defaultValue = "10") @Parameter(description = "페이지 크기") int size,
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호 (0부터 시작)") int page) {
        return ApiResponse.success(roomService.findAllRooms(size, page));
    }

    @Operation(summary = "방 상세 조회 API", description = "방에 대한 상세 조회를 합니다.")
    @GetMapping("/{roomId}")
    public ApiResponse findRoomDetail(
            @Parameter(description = "조회할 방 ID", example = "1")
            @PathVariable Long roomId) {
        return ApiResponse.success(roomService.findRoomDetail(roomId));
    }

    @Operation(summary = "방 참가 API", description = "대기 중인 방에 참가합니다. ")
    @PostMapping("/attention/{roomId}")
    public ApiResponse attentionRoom(
            @Parameter(description = "참가할 방 ID", example = "1")
            @PathVariable Long roomId,
            @RequestBody AttendRequest attendRequest) {
        roomService.attentionRoom(roomId, attendRequest);
        return ApiResponse.success();
    }

    @Operation(summary = "방 나가기 API", description = "참여 중인 방에서 나갑니다. ")
    @PostMapping("/out/{roomId}")
    public ApiResponse outRoom(
            @Parameter(description = "나갈 방 ID", example = "1")
            @PathVariable Long roomId,
            @RequestBody OutRoomRequest outRoomRequest) {
        roomService.outRoom(roomId, outRoomRequest);
        return ApiResponse.success();
    }
}
