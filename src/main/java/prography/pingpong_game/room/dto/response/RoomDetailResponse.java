package prography.pingpong_game.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import prography.pingpong_game.common.utils.DateTimeUtils;
import prography.pingpong_game.room.entity.Room;
@Schema(description = "방 상세 응답 DTO")
public record RoomDetailResponse(
        @Schema(description = "방 ID", example = "1")
        Long id,
        @Schema(description = "방 제목", example = "핑퐁 챔피언십")
        String title,
        @Schema(description = "호스트 ID", example = "100")
        Long hostId,
        @Schema(description = "방 타입 (SINGLE: 단식, DOUBLE: 복식)", example = "SINGLE")
        String roomType,
        @Schema(description = "방 상태 (WAIT: 대기, PROGRESS: 진행 중, FINISH: 완료)", example = "WAIT")
        String status,
        @Schema(description = "방 생성 시간 (yyyy-MM-dd HH:mm:ss)", example = "2024-02-15 10:30:00")
        String createdAt,
        @Schema(description = "방 정보 업데이트 시간 (yyyy-MM-dd HH:mm:ss)", example = "2024-02-15 12:00:00")
        String updatedAt
) {

    public static RoomDetailResponse from(Room room) {
        return new RoomDetailResponse(
                room.getId(),
                room.getTitle(),
                room.getHost().getId(),
                room.getRoomType().name(),
                room.getStatus().name(),
                DateTimeUtils.format(room.getCreatedAt()),
                DateTimeUtils.format(room.getUpdatedAt())
                );
    }
}
