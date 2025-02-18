package prography.pingpong_game.room.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.exception.InvalidRoomTypeException;

@Getter
@AllArgsConstructor
public enum RoomType {
    SINGLE(2),
    DOUBLE(4);

    private final int maxCapacity;

    public static RoomType fromString(String value) {
        for (RoomType type : RoomType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new InvalidRoomTypeException(ApiStatus.BAD_REQUEST);
    }

}
