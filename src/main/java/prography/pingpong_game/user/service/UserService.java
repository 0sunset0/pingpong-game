package prography.pingpong_game.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.user.dto.response.UserPageResponse;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.exception.UserNotFoundException;
import prography.pingpong_game.user.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional(readOnly = true)
    public UserPageResponse findAllUsers(int size, int page) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAllUsers(pageable);
        return UserPageResponse.from(userPage);
    }

    public User getActiveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ApiStatus.BAD_REQUEST));
        user.validateActive();
        return user;
    }
}
