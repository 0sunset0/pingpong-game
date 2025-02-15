package prography.pingpong_game.room.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RoomCreateRequest(
        @Schema(description = "방을 생성할 사용자 ID", example = "1")
        Long userId,
        @Schema(description = "방 타입 (SINGLE: 단식, DOUBLE: 복식)", example = "SINGLE")
        String roomType,
        @Schema(description = "방 제목", example = "프로그라피 최고의 탁구왕")
        String title
){}
