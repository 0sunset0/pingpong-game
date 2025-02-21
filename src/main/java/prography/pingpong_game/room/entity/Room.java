package prography.pingpong_game.room.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.pingpong_game.common.entity.BaseEntity;
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

	private int currentCapacity;
	private int redTeamCapacity;
	private int blueTeamCapacity;

	private Room(String title, User host, RoomType roomType, RoomStatus status) {
		this.title = title;
		this.host = host;
		this.roomType = roomType;
		this.status = status;
	}

	public static Room create(String title, User host, RoomType roomType) {
		return new Room(title, host, roomType, RoomStatus.WAIT);
	}

	public boolean isWaiting() {
		return this.status == RoomStatus.WAIT;
	}

	public boolean isFull() {
		return this.currentCapacity >= this.roomType.getMaxCapacity();
	}

	public Team assignTeam() {
		if (redTeamCapacity == 0) {
			return Team.RED;
		}
		return (redTeamCapacity < this.roomType.getMaxCapacity() / 2) ? Team.RED : Team.BLUE;
	}

	public void addUser(Team team) {
		if (team == Team.RED) {
			redTeamCapacity++;
		} else {
			blueTeamCapacity++;
		}
		this.currentCapacity++;
	}

	public void removeUser(Team team) {
		if (currentCapacity > 0) {
			this.currentCapacity--;
		}
		if (team == Team.RED) {
			redTeamCapacity--;
		} else if (team == Team.BLUE) {
			blueTeamCapacity--;
		}
	}

	public void startGame() {
		this.status = RoomStatus.PROGRESS;
	}

	public void finishGame() {
		this.status = RoomStatus.FINISH;
		this.currentCapacity = 0;
		this.redTeamCapacity = 0;
		this.blueTeamCapacity = 0;
	}

	public boolean isHost(Long userId) {
		return this.host.getId().equals(userId);
	}

	public boolean isExitNotAllowed() {
		return this.status == RoomStatus.PROGRESS || this.status == RoomStatus.FINISH;
	}

	public void changeUserTeam(Team currentTeam, Team newTeam) {
		removeUser(currentTeam);
		addUser(newTeam);
	}

	public boolean canSwitchTeam(Team currentTeam) {
		int maxTeamCapacity = this.roomType.getMaxCapacity() / 2;
		return (currentTeam == Team.RED) ? (blueTeamCapacity < maxTeamCapacity) : (redTeamCapacity < maxTeamCapacity);
	}

}
