package prography.pingpong_game.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import prography.pingpong_game.common.utils.DateTimeUtils;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.entity.UserStatus;
@Schema(description = "사용자 응답 DTO")
public record UserResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long id,
        @Schema(description = "가짜 ID", example = "1001")
        Long fakerId,
        @Schema(description = "사용자 이름", example = "홍길동")
        String name,
        @Schema(description = "사용자 이메일", example = "hong@example.com")
        String email,
        @Schema(description = "사용자 상태 (ACTIVE, INACTIVE, WAIT)", example = "ACTIVE")
        UserStatus status,
        @Schema(description = "사용자 생성 시간 (yyyy-MM-dd HH:mm:ss)", example = "2024-02-15 10:30:00")
        String createdAt,
        @Schema(description = "사용자 정보 업데이트 시간 (yyyy-MM-dd HH:mm:ss)", example = "2024-02-15 12:00:00")
        String updatedAt) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFakerId(),
                user.getName(),
                user.getEmail(),
                user.getStatus(),
                DateTimeUtils.format(user.getCreatedAt()),
                DateTimeUtils.format(user.getUpdatedAt())
        );
    }
}
