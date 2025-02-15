package prography.pingpong_game.initialization.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong_game.common.ApiResponse;
import prography.pingpong_game.initialization.dto.request.InitializationRequest;
import prography.pingpong_game.initialization.service.InitializationService;

@RestController
@RequiredArgsConstructor
public class InitializationController {
    private final InitializationService initializationService;

    @Operation(summary = "초기화 API", description = "데이터를 초기화합니다.")
    @PostMapping("/init")
    public ApiResponse initialize(@RequestBody InitializationRequest initializationRequest) {
        initializationService.initialize(initializationRequest.seed(), initializationRequest.quantity());
        return ApiResponse.success();
    }
}
