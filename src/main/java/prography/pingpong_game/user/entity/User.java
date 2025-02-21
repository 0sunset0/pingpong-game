package prography.pingpong_game.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.pingpong_game.common.entity.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long fakerId;
	private String name;
	private String email;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private User(Long fakerId, String name, String email, UserStatus status) {
		this.fakerId = fakerId;
		this.name = name;
		this.email = email;
		this.status = status;
	}

	public static User create(Long fakerId, String name, String email, UserStatus status) {
		return new User(fakerId, name, email, status);
	}

	public boolean isActive() {
		return this.status == UserStatus.ACTIVE;
	}
}
