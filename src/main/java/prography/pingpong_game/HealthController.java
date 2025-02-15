package prography.pingpong_game;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ApiResponse healthCheck() {
        return ApiResponse.success();
    }
}
