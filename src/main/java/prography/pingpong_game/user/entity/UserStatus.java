package prography.pingpong_game.user.entity;

public enum UserStatus {
    WAIT, ACTIVE, NON_ACTIVE;
    public static UserStatus fromFakerId(Long fakerId, long activeThreshold, long waitThreshold) {
        if (fakerId <= activeThreshold) {
            return ACTIVE;
        } else if (fakerId <= waitThreshold) {
            return WAIT;
        } else {
            return NON_ACTIVE;
        }
    }
}
