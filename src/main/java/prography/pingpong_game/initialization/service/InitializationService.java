package prography.pingpong_game.initialization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong_game.initialization.infra.FakerApiClient;
import prography.pingpong_game.initialization.infra.FakerApiResponse;
import prography.pingpong_game.initialization.infra.FakerUserData;
import prography.pingpong_game.room.repository.RoomRepository;
import prography.pingpong_game.room.repository.UserRoomRepository;
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
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private static final int activeUserMaxFakerId = 30;
    private static final int waitUserMaxFakerId = 60;

    @Transactional
    public void initialize(int seed, int quantity) {
        clearData();  // 기존 데이터를 삭제
        FakerApiResponse fakerApiResponse = fakerApiClient.fetchUsers(seed, quantity, Locale.KOREA).block();
        if (fakerApiResponse != null) {
            List<User> users = fakerApiResponse.data().stream()
                    .map(this::convertToUser)
                    .sorted(Comparator.comparing(User::getFakerId))
                    .toList();
            userRepository.saveAll(users);
        }
    }

    private void clearData() {
        userRoomRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
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
