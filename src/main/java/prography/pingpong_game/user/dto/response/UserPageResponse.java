package prography.pingpong_game.user.dto.response;

import org.springframework.data.domain.Page;
import prography.pingpong_game.user.entity.User;

import java.util.List;

public record UserPageResponse<T>(
        long totalElements,
        int totalPages,
        List<UserResponse> userList) {

    public static UserPageResponse from(Page<User> userPage) {
        return new UserPageResponse(
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.getContent().stream()
                        .map(UserResponse::from)
                        .toList()
        );
    }
}
