package prography.pingpong_game.user.service;


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
import prography.pingpong_game.user.dto.response.UserPageResponse;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.entity.UserStatus;
import prography.pingpong_game.user.exception.UserNotActiveException;
import prography.pingpong_game.user.exception.UserNotFoundException;
import prography.pingpong_game.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
		activeUser = User.create(1L, "Active User", "active@example.com", UserStatus.ACTIVE);
		inactiveUser = User.create(2L, "Inactive User", "inactive@example.com", UserStatus.NON_ACTIVE);
	}

//	@Test
//	@DisplayName("전체 유저 조회 : 페이징된 유저 목록을 반환한다.")
//	void findAllUsers_ShouldReturnPagedUsers() {
//		// Given
//		List<User> users = Arrays.asList(activeUser, inactiveUser);
//		PageRequest pageable = PageRequest.of(0, 10);
//		Page<User> userPage = new PageImpl<>(users, pageable, users.size());
//
//		when(userRepository.findAllUsers(any(PageRequest.class))).thenReturn(userPage);
//
//		// When
//		UserPageResponse response = userService.findAllUsers(10, 0);
//
//		// Then
//		assertThat(response.userList()).hasSize(2);
//		verify(userRepository, times(1)).findAllUsers(any(PageRequest.class));
//	}

	@Test
	@DisplayName("활성 상태 유저 조회 - 존재하는 경우 유저를 반환한다.")
	void getActiveUser_ShouldReturnActiveUser_WhenUserExistsAndActive() {
		// Given
		when(userRepository.findById(1L)).thenReturn(Optional.of(activeUser));

		// When
		User result = userService.getActiveUser(1L);

		// Then
		assertThat(result).isEqualTo(activeUser);
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