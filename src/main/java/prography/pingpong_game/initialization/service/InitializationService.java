package prography.pingpong_game.initialization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong_game.initialization.infra.FakerApiClient;
import prography.pingpong_game.initialization.infra.FakerApiResponse;
import prography.pingpong_game.initialization.infra.FakerUserData;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.entity.UserStatus;
import prography.pingpong_game.user.repository.UserRepository;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class InitializationService {
    private final FakerApiClient fakerApiClient;
    private final UserRepository userRepository;

    @Transactional
    public void initialize(int seed, int quantity) {
        //TODO: 기존에 있던 모든 회원 정보 및 방 정보를 삭제

        FakerApiResponse fakerApiResponse = fakerApiClient.fetchUsers(seed, quantity, Locale.KOREA);
        List<User> users = fakerApiResponse.data().stream()
                .map(this::convertToUserEntity)
                .sorted((u1, u2) -> u1.getFakerId().compareTo(u2.getFakerId()))
                .toList();
        userRepository.saveAll(users);
    }

    private User convertToUserEntity(FakerUserData fakerUserData) {
        return User.create(
                fakerUserData.id(),
                fakerUserData.username(),
                fakerUserData.email(),
                determineUserStatus(fakerUserData.id())
        );
    }

    //TODO : 이 부분을 추상화할 수는 없을까
    private UserStatus determineUserStatus(Long fakerId) {
        if (fakerId <= 30) {
            return UserStatus.ACTIVE;
        } else if (fakerId <= 60) {
            return UserStatus.WAIT;
        } else {
            return UserStatus.NON_ACTIVE;
        }
    }
}
