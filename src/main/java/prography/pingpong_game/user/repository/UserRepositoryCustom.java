package prography.pingpong_game.user.repository;


import org.springframework.data.domain.Page;
import prography.pingpong_game.user.entity.User;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    Page<User> findAllUsers(Pageable pageable);
}
