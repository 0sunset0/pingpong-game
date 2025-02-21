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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_capacity_id")
    private RoomCapacity roomCapacity;

    private Room(String title, User host, RoomType roomType, RoomStatus status, RoomCapacity roomCapacity) {
        this.title = title;
        this.host = host;
        this.roomType = roomType;
        this.status = status;
        this.roomCapacity = roomCapacity;
    }

    public static Room create(String title, User host, RoomType roomType) {
        RoomCapacity roomCapacity = RoomCapacity.create(roomType.getMaxCapacity());
        return new Room(title, host, roomType, RoomStatus.WAIT, roomCapacity);
    }

    public boolean isWaiting() {
        return this.status == RoomStatus.WAIT;
    }
    public boolean isFull() {
        return this.roomCapacity.isFull();
    }

    public Team assignTeam() {
        return roomCapacity.assignTeam();
    }

    public void addUser(UserRoom userRoom) {
        roomCapacity.addUserToTeam(userRoom.getTeam());
    }

    public void removeUser(UserRoom userRoom) {
        roomCapacity.removeUserFromTeam(userRoom.getTeam());
    }

    public void startGame() {
        this.status = RoomStatus.PROGRESS;
    }

    public void finishGame() {
        this.status = RoomStatus.FINISH;
        roomCapacity.removeAllUsers();
    }

    public boolean isHost(Long userId) {
        return this.host.getId().equals(userId);
    }

    public boolean isExitNotAllowed() {
        return this.status == RoomStatus.PROGRESS || this.status == RoomStatus.FINISH;
    }

    public void changeUserTeam(Team currentTeam, Team newTeam) {
        this.roomCapacity.removeUserFromTeam(currentTeam);
        this.roomCapacity.addUserToTeam(newTeam);
    }
}
