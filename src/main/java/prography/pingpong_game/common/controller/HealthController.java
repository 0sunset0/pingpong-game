package prography.pingpong_game.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong_game.common.dto.ApiResponse;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Operation(summary = "헬스 체크", description = "서버의 상태를 체크하는 API입니다.")
    @GetMapping
    public ApiResponse healthCheck() {
        return ApiResponse.success();
    }
}
