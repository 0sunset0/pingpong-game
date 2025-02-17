package prography.pingpong_game.room.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.pingpong_game.BaseEntity;
import prography.pingpong_game.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host", nullable = false)
    private User host;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;
    private Room(String title, User host, RoomType roomType, RoomStatus status) {
        this.title = title;
        this.host = host;
        this.roomType = roomType;
        this.status = status;
    }

    public static Room create(String title, User host, RoomType roomType) {
        return new Room(title, host, roomType, RoomStatus.WAIT);
    }
}
