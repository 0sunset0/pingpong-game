package prography.pingpong_game.room.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.pingpong_game.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Team team;

    @Enumerated(EnumType.STRING)
    private UserRoomStatus status;

    public static UserRoom create(Room room, User user, Team team) {
        return new UserRoom(room, user, team, UserRoomStatus.ACTIVE);
    }

    private UserRoom(Room room, User user, Team team, UserRoomStatus status) {
        this.room = room;
        this.user = user;
        this.team = team;
        this.status = status;
    }

    public void changeTeam() {
        this.team = (this.team == Team.RED) ? Team.BLUE : Team.RED;
    }
}
