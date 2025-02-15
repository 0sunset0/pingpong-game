package prography.pingpong_game.user.dto;

import org.springframework.data.domain.Page;
import prography.pingpong_game.user.entity.User;

import java.util.List;

public record UserPageResponse<T>(
        long totalElements,
        int totalPages,
        List<UserResponse> userList) {
        public UserPageResponse(Page<User> userPage) {
            this(
                    userPage.getTotalElements(),
                    userPage.getTotalPages(),
                    userPage.getContent().stream()
                            .map(UserResponse::new)
                            .toList()
            );
        }
}
