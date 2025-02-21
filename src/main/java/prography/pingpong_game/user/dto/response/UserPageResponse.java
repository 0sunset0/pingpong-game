package prography.pingpong_game.user.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import prography.pingpong_game.user.entity.User;

public record UserPageResponse(
	@Schema(description = "전체 요소 개수", example = "100")
	long totalElements,
	@Schema(description = "전체 페이지 개수", example = "10")
	int totalPages,
	List<UserSummaryResponse> userList
) {

	public static UserPageResponse from(Page<User> userPage) {
		return new UserPageResponse(
			userPage.getTotalElements(),
			userPage.getTotalPages(),
			userPage.getContent().stream()
				.map(UserSummaryResponse::from)
				.toList()
		);
	}
}
