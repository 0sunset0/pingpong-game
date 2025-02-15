package prography.pingpong_game.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import prography.pingpong_game.user.entity.User;


public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
