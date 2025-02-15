package prography.pingpong_game.initialization.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class FakerApiClient {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://fakerapi.it/api/v1/users";

    public FakerApiResponse fetchUsers(int seed, int quantity, Locale locale) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam(FakerApiQueryParam.SEED.getValue(), seed)
                .queryParam(FakerApiQueryParam.QUANTITY.getValue(), quantity)
                .queryParam(FakerApiQueryParam.LOCALE.getValue(), locale.toString())
                .toUriString();
        return restTemplate.getForObject(url, FakerApiResponse.class);
    }
}
