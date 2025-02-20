package prography.pingpong_game.room.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RoomCapacity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int maxCapacity;
	private int currentCapacity;
	private int redTeamCapacity;
	private int blueTeamCapacity;

	private RoomCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public static RoomCapacity create(int maxCapacity) {
		return new RoomCapacity(maxCapacity);
	}

	Team assignTeam() {
		if (redTeamCapacity == 0) {
			return Team.RED;
		}
		return (redTeamCapacity < maxCapacity / 2) ? Team.RED : Team.BLUE;
	}

	public void addUserToTeam(Team team) {
		if (team == Team.RED) {
			redTeamCapacity++;
		} else {
			blueTeamCapacity++;
		}
		this.currentCapacity++;
	}

	public void removeUserFromTeam(Team team) {
		if (currentCapacity > 0) {
			this.currentCapacity--;
		}
		if (team == Team.RED && redTeamCapacity > 0) {
			redTeamCapacity--;
		} else if (team == Team.BLUE && blueTeamCapacity > 0) {
			blueTeamCapacity--;
		}
	}
	boolean isFull() {
		return this.currentCapacity >= this.maxCapacity;
	}

	public void removeAllUsers() {
		this.currentCapacity = 0;
		this.redTeamCapacity = 0;
		this.blueTeamCapacity = 0;
	}

	public boolean canSwitchTeam(Team currentTeam) {
		int maxTeamCapacity = maxCapacity / 2;
		return (currentTeam == Team.RED) ? (blueTeamCapacity < maxTeamCapacity) : (redTeamCapacity < maxTeamCapacity);
	}
}
