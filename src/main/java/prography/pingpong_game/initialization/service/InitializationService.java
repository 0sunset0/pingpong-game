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

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class InitializationService {
    private final FakerApiClient fakerApiClient;
    private final UserRepository userRepository;

    //TODO yml에서 받아서 쓸지 고민
    private static final int activeUserMaxFakerId = 30;
    private static final int waitUserMaxFakerId = 60;

    @Transactional
    public void initialize(int seed, int quantity) {
        //TODO: 기존에 있던 모든 회원 정보 및 방 정보를 삭제(방 정보 삭제 해야 함)
        userRepository.deleteAll();
        fakerApiClient.fetchUsers(seed, quantity, Locale.KOREA)
                .subscribe(fakerApiResponse -> {
                    List<User> users = fakerApiResponse.data().stream()
                            .map(this::convertToUser)
                            .sorted(Comparator.comparing(User::getFakerId))
                            .toList();
                    userRepository.saveAll(users);
                });
    }

    private User convertToUser(FakerUserData fakerUserData) {
        return User.create(
                fakerUserData.id(),
                fakerUserData.username(),
                fakerUserData.email(),
                determineUserStatus(fakerUserData.id())
        );
    }
    private UserStatus determineUserStatus(Long fakerId) {
        return UserStatus.fromFakerId(fakerId, activeUserMaxFakerId, waitUserMaxFakerId);
    }
}
