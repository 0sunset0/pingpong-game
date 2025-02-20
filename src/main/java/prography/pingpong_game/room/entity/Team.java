package prography.pingpong_game.room.entity;

import lombok.Getter;

@Getter
public enum Team {
    RED, BLUE;

    public Team getOpposite() {
        return (this == RED) ? BLUE : RED;
    }
}
