package prography.pingpong_game.initialization.infra;

import lombok.Getter;

@Getter
enum FakerApiQueryParam {

    SEED("_seed"),
    QUANTITY("_quantity"),
    LOCALE("_locale");

    private final String value;

    FakerApiQueryParam(String value) {
        this.value = value;
    }
}
