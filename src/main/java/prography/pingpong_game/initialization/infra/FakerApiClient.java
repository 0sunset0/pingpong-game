package prography.pingpong_game.initialization.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class FakerApiClient {
    private final WebClient webClient;
    private static final String BASE_URL = "https://fakerapi.it/api/v1";
    private static final String USERS_ENDPOINT = "/users";

    public Mono<FakerApiResponse> fetchUsers(int seed, int quantity, Locale locale) {
        return webClient.get()
                .uri(UriComponentsBuilder.fromHttpUrl(BASE_URL + USERS_ENDPOINT)
                        .queryParam(FakerApiQueryParam.SEED.getValue(), seed)
                        .queryParam(FakerApiQueryParam.QUANTITY.getValue(), quantity)
                        .queryParam(FakerApiQueryParam.LOCALE.getValue(), locale.toString())
                        .toUriString())
                .retrieve()
                .bodyToMono(FakerApiResponse.class);
    }
}
