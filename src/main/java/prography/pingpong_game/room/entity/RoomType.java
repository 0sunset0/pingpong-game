package prography.pingpong_game.room.entity;

import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.exception.InvalidRoomTypeException;

public enum RoomType {
    SINGLE, DOUBLE;

    public static RoomType fromString(String value) {
        for (RoomType type : RoomType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new InvalidRoomTypeException(ApiStatus.BAD_REQUEST);
    }

}
