package prography.pingpong_game.user.dto.response;

import prography.pingpong_game.common.utils.DateTimeUtils;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.entity.UserStatus;

public record UserResponse(
        Long id,
        Integer fakerId,
        String name,
        String email,
        UserStatus status,
        String createdAt,
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
