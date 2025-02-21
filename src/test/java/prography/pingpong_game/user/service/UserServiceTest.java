package prography.pingpong_game.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import prography.pingpong_game.user.dto.response.UserPageResponse;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.entity.UserStatus;
import prography.pingpong_game.user.exception.UserNotActiveException;
import prography.pingpong_game.user.exception.UserNotFoundException;
import prography.pingpong_game.user.repository.UserRepository;

@DisplayName("유저 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User activeUser;
	private User inactiveUser;

	@BeforeEach
	void setUp() {
		activeUser = User.create(1L, "Active User", "active@naver.com", UserStatus.ACTIVE);
		inactiveUser = User.create(2L, "Inactive User", "inactive@naver.com", UserStatus.NON_ACTIVE);
		ReflectionTestUtils.setField(activeUser, "id", 1L);
		ReflectionTestUtils.setField(inactiveUser, "id", 2L);
		ReflectionTestUtils.setField(activeUser, "createdAt", LocalDateTime.now());
		ReflectionTestUtils.setField(activeUser, "updatedAt", LocalDateTime.now());
		ReflectionTestUtils.setField(inactiveUser, "createdAt", LocalDateTime.now());
		ReflectionTestUtils.setField(inactiveUser, "updatedAt", LocalDateTime.now());
	}

	@Test
	@DisplayName("전체 유저 조회 : 페이징된 유저 목록을 반환한다.")
	void findAllUsers_ShouldReturnPagedUsers() {
		// Given
		List<User> users = Arrays.asList(activeUser, inactiveUser);
		PageRequest pageable = PageRequest.of(0, 10);
		Page<User> userPage = new PageImpl<>(users, pageable, users.size());

		when(userRepository.findAllUsers(any(PageRequest.class))).thenReturn(userPage);

		// When
		UserPageResponse response = userService.findAllUsers(10, 0);

		// Then
		assertThat(response).isNotNull(); // 응답이 null이 아닌지 확인
		assertThat(response.userList()).hasSize(2); // 응답 리스트 크기 확인
		assertThat(response.userList().get(0).id()).isEqualTo(activeUser.getId()); // 첫 번째 유저 ID 검증
		assertThat(response.userList().get(1).id()).isEqualTo(inactiveUser.getId()); // 두 번째 유저 ID 검증

		verify(userRepository, times(1)).findAllUsers(any(PageRequest.class));
	}

	@Test
	@DisplayName("활성 상태 유저 조회 - 존재하는 경우 유저를 반환한다.")
	void getActiveUser_ShouldReturnActiveUser_WhenUserExistsAndActive() {
		// Given
		when(userRepository.findById(1L)).thenReturn(Optional.of(activeUser));

		// When
		User result = userService.getActiveUser(1L);

		// Then
		assertThat(result.isActive()).isEqualTo(true);
		verify(userRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("활성 상태 유저 조회 - 존재하지 않는 경우 UserNotFoundException")
	void getActiveUser_ShouldThrowException_WhenUserNotFound() {
		// Given
		when(userRepository.findById(99L)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(UserNotFoundException.class, () -> userService.getActiveUser(99L));
		verify(userRepository, times(1)).findById(99L);
	}

	@Test
	@DisplayName("활성 상태의 유저 조회 - 비활성 상태일 경우 UserNotActiveException")
	void getActiveUser_ShouldThrowException_WhenUserIsInactive() {
		// Given
		when(userRepository.findById(2L)).thenReturn(Optional.of(inactiveUser));

		// When & Then
		assertThrows(UserNotActiveException.class, () -> userService.getActiveUser(2L));
		verify(userRepository, times(1)).findById(2L);
	}

}