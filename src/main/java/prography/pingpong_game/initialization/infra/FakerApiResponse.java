package prography.pingpong_game.initialization.infra;

import java.util.List;

public record FakerApiResponse(
        List<FakerUserData> data
) {
}
