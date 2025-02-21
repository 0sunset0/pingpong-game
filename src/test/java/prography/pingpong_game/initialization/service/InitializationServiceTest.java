package prography.pingpong_game.initialization.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import prography.pingpong_game.initialization.infra.FakerApiClient;
import prography.pingpong_game.initialization.infra.FakerApiResponse;
import prography.pingpong_game.initialization.infra.FakerUserData;
import prography.pingpong_game.room.repository.RoomRepository;
import prography.pingpong_game.room.repository.UserRoomRepository;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.entity.UserStatus;
import prography.pingpong_game.user.repository.UserRepository;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class InitializationServiceTest {

	@Mock
	private FakerApiClient fakerApiClient;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private UserRoomRepository userRoomRepository;

	@InjectMocks
	private InitializationService initializationService;

	private FakerUserData userData1, userData2;
	private FakerApiResponse fakerApiResponse;

	@BeforeEach
	void setUp() {
		userData1 = new FakerUserData(1L, "User1", "user1@naver.com");
		userData2 = new FakerUserData(2L, "User2", "user2@naver.com");

		fakerApiResponse = new FakerApiResponse(Arrays.asList(userData1, userData2));
	}

	@Test
	@DisplayName("데이터 초기화 - API 호출 후 유저 저장")
	void initialize_FetchesAndSavesUsers() {
		// Given
		when(fakerApiClient.fetchUsers(anyInt(), anyInt(), any(Locale.class)))
			.thenReturn(Mono.just(fakerApiResponse));

		// When
		initializationService.initialize(1, 2);

		// Then
		verify(userRoomRepository, times(1)).deleteAll();
		verify(roomRepository, times(1)).deleteAll();
		verify(userRepository, times(1)).deleteAll();
		verify(fakerApiClient, times(1)).fetchUsers(anyInt(), anyInt(), any(Locale.class));
		verify(userRepository, times(1)).saveAll(anyList());
	}

	@Test
	@DisplayName("데이터 초기화 - API 응답이 null일 경우 저장하지 않음")
	void initialize_DoesNotSave_WhenNull() {
		// Given
		when(fakerApiClient.fetchUsers(anyInt(), anyInt(), any(Locale.class)))
			.thenReturn(Mono.empty());

		// When
		initializationService.initialize(1, 2);

		// Then
		verify(userRepository, never()).saveAll(anyList());
	}

	@Test
	@DisplayName("유저 변환 테스트")
	void convertToUser() {
		// When
		User user = initializationService.convertToUser(userData1);

		// Then
		assertThat(user.getFakerId()).isEqualTo(userData1.id());
		assertThat(user.getName()).isEqualTo(userData1.username());
		assertThat(user.getEmail()).isEqualTo(userData1.email());
	}

	@Test
	@DisplayName("FakerId에 따른 유저 상태 결정 테스트 - ACTIVE 상태")
	void determineUserStatus_Active() {
		// When
		UserStatus status = initializationService.determineUserStatus(30L);

		// Then
		assertThat(status).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	@DisplayName("FakerId에 따른 유저 상태 결정 테스트 - WAIT 상태")
	void determineUserStatus_Wait() {
		// When
		UserStatus status = initializationService.determineUserStatus(31L);

		// Then
		assertThat(status).isEqualTo(UserStatus.WAIT);
	}

	@Test
	@DisplayName("FakerId에 따른 유저 상태 결정 - INACTIVE 상태")
	void determineUserStatus_Inactive() {
		// When
		UserStatus status = initializationService.determineUserStatus(70L);

		// Then
		assertThat(status).isEqualTo(UserStatus.NON_ACTIVE);
	}
}
