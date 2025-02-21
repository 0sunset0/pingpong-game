package prography.pingpong_game.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong_game.common.dto.ApiResponse;
import prography.pingpong_game.user.dto.response.UserPageResponse;
import prography.pingpong_game.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Operation(summary = "유저 전체 조회 API", description = "모든 회원 정보를 응답합니다.")
    @GetMapping
    public ApiResponse<UserPageResponse> findAllUsers(
            @RequestParam(defaultValue = "10") @Parameter(description = "페이지 크기") int size,
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호 (0부터 시작)") int page
    ) {
        return ApiResponse.success(userService.findAllUsers(size, page));
    }
}
