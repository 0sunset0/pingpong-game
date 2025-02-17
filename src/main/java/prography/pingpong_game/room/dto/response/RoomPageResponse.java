package prography.pingpong_game.room.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import prography.pingpong_game.room.entity.Room;

import java.util.List;

@Schema(description = "방 전체 조회 응답 DTO")
public record RoomPageResponse(
        @Schema(description = "전체 요소 개수", example = "100")
        long totalElements,
        @Schema(description = "전체 페이지 개수", example = "10")
        int totalPages,
        List<RoomSummaryResponse> roomList
) {
    public static RoomPageResponse from(Page<Room> roomPage) {
        List<RoomSummaryResponse> roomList = roomPage.getContent().stream()
                .map(RoomSummaryResponse::from)
                .toList();
        return new RoomPageResponse(
                roomPage.getTotalElements(),
                roomPage.getTotalPages(),
                roomList
        );
    }
}
