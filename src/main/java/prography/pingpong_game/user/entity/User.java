package prography.pingpong_game.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.pingpong_game.BaseEntity;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.user.exception.UserNotActiveException;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@AllArgsConstructor
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

    // ✅ 팩토리 메서드 추가
    public static User create(Long fakerId, String name, String email, UserStatus status) {
        return new User(fakerId, name, email, status);
    }

    private boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public void validateActive() {
        if (!isActive()) {
            throw new UserNotActiveException(ApiStatus.BAD_REQUEST);
        }
    }
}
