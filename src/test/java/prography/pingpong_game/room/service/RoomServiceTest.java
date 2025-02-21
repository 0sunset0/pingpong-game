package prography.pingpong_game.room.service;

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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import prography.pingpong_game.room.dto.request.AttendRequest;
import prography.pingpong_game.room.dto.request.OutRoomRequest;
import prography.pingpong_game.room.dto.request.RoomCreateRequest;
import prography.pingpong_game.room.dto.request.StartGameRequest;
import prography.pingpong_game.room.dto.response.RoomDetailResponse;
import prography.pingpong_game.room.dto.response.RoomPageResponse;
import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.entity.RoomType;
import prography.pingpong_game.room.entity.Team;
import prography.pingpong_game.room.entity.UserRoom;
import prography.pingpong_game.room.exception.RoomNotFoundException;
import prography.pingpong_game.room.repository.RoomRepository;
import prography.pingpong_game.room.repository.UserRoomRepository;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.entity.UserStatus;
import prography.pingpong_game.user.service.UserService;

@DisplayName("방 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

	@Mock
	private RoomRepository roomRepository;
	@Mock
	private UserService userService;
	@Mock
	private UserRoomValidator userRoomValidator;
	@Mock
	private RoomValidator roomValidator;
	@Mock
	private UserRoomService userRoomService;
	@Mock
	private ApplicationEventPublisher eventPublisher;
	@Mock
	private UserRoomRepository userRoomRepository;

	@InjectMocks
	private RoomService roomService;

	private User testUser;
	private Room testRoom;

	@BeforeEach
	void setUp() {
		testUser = User.create(1L, "Test User", "test@example.com", UserStatus.ACTIVE);
		testRoom = Room.create("Test Room", testUser, RoomType.SINGLE);
		ReflectionTestUtils.setField(testUser, "id", 1L);
		ReflectionTestUtils.setField(testRoom, "id", 1L);
		ReflectionTestUtils.setField(testRoom, "createdAt", LocalDateTime.now());
		ReflectionTestUtils.setField(testRoom, "updatedAt", LocalDateTime.now());
	}

	@Test
	@DisplayName("방 상세 조회 - 존재하는 방을 반환한다")
	void findRoom_WhenExists() {
		// Given
		when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));

		// When
		RoomDetailResponse response = roomService.findRoomDetail(1L);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.title()).isEqualTo(testRoom.getTitle());
		verify(roomRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("방 상세 조회 - 존재하지 않는 방이면 RoomNotFoundException")
	void findRoom_WhenNotExists() {
		// Given
		when(roomRepository.findById(99L)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(RoomNotFoundException.class, () -> roomService.findRoomDetail(99L));
		verify(roomRepository, times(1)).findById(99L);
	}

	@Test
	@DisplayName("전체 방 조회 - 페이징된 방 목록을 반환한다")
	void findAllRooms() {
		// Given
		List<Room> rooms = Arrays.asList(testRoom);
		PageRequest pageable = PageRequest.of(0, 10);
		Page<Room> roomPage = new PageImpl<>(rooms, pageable, rooms.size());

		when(roomRepository.findAllRooms(any(PageRequest.class))).thenReturn(roomPage);

		// When
		RoomPageResponse response = roomService.findAllRooms(10, 0);

		// Then
		assertThat(response.roomList()).hasSize(1);
		verify(roomRepository, times(1)).findAllRooms(any(PageRequest.class));
	}

	@Test
	@DisplayName("방 생성 - 정상적으로 생성된다")
	void createRoom_Success() {
		// Given
		RoomCreateRequest request = new RoomCreateRequest(1L, "SINGLE", "room");
		when(userService.getActiveUser(1L)).thenReturn(testUser);
		when(roomRepository.save(any(Room.class))).thenReturn(testRoom);
		when(roomRepository.findById(any(Long.class))).thenReturn(Optional.of(testRoom));
		doNothing().when(userRoomValidator).validateUserNotInAnyRoom(anyLong());

		// When
		roomService.createRoom(request);

		// Then
		verify(roomRepository, times(1)).save(any(Room.class));
	}

	// @Test
	// @DisplayName("방 생성 - 유저가 이미 참여하고 있는 방이 있다면 UserAlreadyInRoomException 발생")
	// void createRoom_WhenUserAlreadyInRoom_ThrowsException() {
	// 	// Given
	// 	RoomCreateRequest request = new RoomCreateRequest(1L, "SINGLE", "room");
	//
	// 	doThrow(new UserAlreadyInRoomException(ApiStatus.BAD_REQUEST))
	// 		.when(userRoomValidator).validateUserNotInAnyRoom(anyLong());
	//
	// 	// When & Then
	// 	assertThrows(UserAlreadyInRoomException.class, () -> roomService.createRoom(request));
	// }

	@Test
	@DisplayName("방 입장 - 정상적으로 입장한다")
	void joinRoom_Success() {
		// Given
		AttendRequest request = new AttendRequest(1L);
		when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
		when(userService.getActiveUser(1L)).thenReturn(testUser);

		// When
		roomService.attentionRoom(1L, request);

		// Then
		verify(roomRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("방 나가기 - 호스트가 나가면 방이 종료됨")
	void outRoom_WhenHostLeaves() {
		// Given
		when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
		when(userRoomService.findUserRoom(1L, testUser.getId())).thenReturn(
			UserRoom.create(testRoom, testUser, Team.RED));

		// When
		roomService.outRoom(1L, new OutRoomRequest(testUser.getId()));

		// Then
		verify(userRoomService, times(1)).deleteAllUserRooms(1L);
	}

	@Test
	@DisplayName("방 나가기 - 호스트가 아니라면 방이 종료되지 않음")
	void leaveRoom_WhenNotHost_RoomRemains() {
		// Given
		User anotherUser = User.create(2L, "Another User", "another@example.com", UserStatus.ACTIVE);
		ReflectionTestUtils.setField(anotherUser, "id", 2L);

		// When
		when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
		when(userRoomService.findUserRoom(1L, anotherUser.getId())).thenReturn(
			UserRoom.create(testRoom, anotherUser, Team.RED));

		roomService.outRoom(1L, new OutRoomRequest(anotherUser.getId()));

		// Then
		verify(userRoomService, times(1)).deleteUser(any(UserRoom.class));
	}

	@Test
	@DisplayName("게임 시작 - 방장이 게임을 시작하면 이벤트가 발생한다")
	void startGame() {
		// Given
		StartGameRequest request = new StartGameRequest(1L);
		when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));

		// When
		roomService.startGame(1L, request);

		// Then
		verify(roomRepository, times(1)).findById(1L);
	}
}
