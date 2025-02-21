package prography.pingpong_game.room.service;

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
import prography.pingpong_game.room.dto.request.*;
import prography.pingpong_game.room.dto.response.RoomDetailResponse;
import prography.pingpong_game.room.dto.response.RoomPageResponse;
import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.entity.RoomType;
import prography.pingpong_game.room.exception.RoomNotFoundException;
import prography.pingpong_game.room.repository.RoomRepository;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.entity.UserStatus;
import prography.pingpong_game.user.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private RoomService roomService;

	private User testUser;
	private Room testRoom;

	@BeforeEach
	void setUp() {
		testUser = User.create(1L, "Test User", "test@example.com", UserStatus.ACTIVE);
		testRoom = Room.create("Test Room", testUser, RoomType.SINGLE);
	}

	@Test
	@DisplayName("방 상세 조회 - 존재하는 방을 반환한다")
	void findRoomDetail_ShouldReturnRoom_WhenRoomExists() {
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
	@DisplayName("방 상세 조회 - 존재하지 않는 방이면 예외 발생")
	void findRoomDetail_ShouldThrowException_WhenRoomNotFound() {
		// Given
		when(roomRepository.findById(99L)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(RoomNotFoundException.class, () -> roomService.findRoomDetail(99L));
		verify(roomRepository, times(1)).findById(99L);
	}

	@Test
	@DisplayName("전체 방 조회 - 페이징된 방 목록을 반환한다")
	void findAllRooms_ShouldReturnPagedRooms() {
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
	void createRoom_ShouldCreateRoomSuccessfully() {
		// Given
		RoomCreateRequest request = new RoomCreateRequest(1L, "SINGLE", "room");
		when(userService.getActiveUser(1L)).thenReturn(testUser);
		when(roomRepository.save(any(Room.class))).thenReturn(testRoom);

		// When
		roomService.createRoom(request);

		// Then
		verify(roomRepository, times(1)).save(any(Room.class));
	}

	@Test
	@DisplayName("방 입장 - 정상적으로 입장한다")
	void attentionRoom_ShouldAllowUserToJoin() {
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
	@DisplayName("방 나가기 - 정상적으로 나갈 수 있다")
	void outRoom_ShouldAllowUserToExit() {
		// Given
		OutRoomRequest request = new OutRoomRequest(1L);
		when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));

		// When
		roomService.outRoom(1L, request);

		// Then
		verify(roomRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("게임 시작 - 방장이 게임을 시작하면 이벤트가 발생한다")
	void startGame_ShouldTriggerEvent_WhenHostStartsGame() {
		// Given
		StartGameRequest request = new StartGameRequest(1L);
		when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));

		// When
		roomService.startGame(1L, request);

		// Then
		verify(roomRepository, times(1)).findById(1L);
	}
}
