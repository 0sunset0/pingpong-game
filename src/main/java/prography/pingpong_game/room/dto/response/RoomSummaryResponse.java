package prography.pingpong_game.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import prography.pingpong_game.room.entity.Room;
@Schema(description = "방 요약 정보 응답 DTO")
public record RoomSummaryResponse(
        @Schema(description = "방 ID", example = "1")
        Long id,
        @Schema(description = "방 제목", example = "테스트 방")
        String title,
        @Schema(description = "방장(Host)의 ID", example = "123")
        Long hostId,
        @Schema(description = "방 타입 (SINGLE: 단식, DOUBLE: 복식)", example = "SINGLE")
        String roomType,
        @Schema(description = "방 상태 (WAIT: 대기, PROGRESS: 진행중, FINISH: 완료)", example = "WAIT")
        String status
) {
    public static RoomSummaryResponse from(Room room) {
        return new RoomSummaryResponse(
                room.getId(),
                room.getTitle(),
                room.getHost().getId(),
                room.getRoomType().name(),
                room.getStatus().name()
        );
    }

}
