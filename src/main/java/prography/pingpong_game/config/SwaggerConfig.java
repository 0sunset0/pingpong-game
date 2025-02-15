package prography.pingpong_game.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "PingPong Game API",
                description = "프로그라피 10기 과제 (탁구 게임 API) 문서"
        )
)
public class SwaggerConfig {
}
