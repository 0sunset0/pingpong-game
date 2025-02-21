package prography.pingpong_game.room.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.entity.Team;
import prography.pingpong_game.room.exception.OnlyHostCanStartGameException;
import prography.pingpong_game.room.exception.RoomIsFullException;
import prography.pingpong_game.room.exception.RoomIsNotFullException;
import prography.pingpong_game.room.exception.RoomNotExitException;
import prography.pingpong_game.room.exception.RoomNotWaitingException;
import prography.pingpong_game.room.exception.TeamSwitchNotAllowed;

@ExtendWith(MockitoExtension.class)
class RoomValidatorTest {
	@InjectMocks
	private RoomValidator roomValidator;

	@Mock
	private Room room;

	@BeforeEach
	void setUp() {
		room = mock(Room.class);
	}

	@Test
	@DisplayName("방 나가기 - 나갈 수 없는 방이면 RoomNotExitException")
	void validateCanExitRoom_WhenExitNotAllowed_ThrowsException() {
		when(room.isExitNotAllowed()).thenReturn(true);

		assertThatThrownBy(() -> roomValidator.validateCanExitRoom(room))
			.isInstanceOf(RoomNotExitException.class);
	}

	@Test
	@DisplayName("방 나가기 - 나갈 수 있는 경우 성공")
	void validateCanExitRoom_WhenExitAllowed_DoesNotThrow() {
		when(room.isExitNotAllowed()).thenReturn(false);
		assertThatCode(() -> roomValidator.validateCanExitRoom(room)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("방 나가기 - 방이 가득 찬 경우 RoomIsFullException")
	void validateRoomNotFull_WhenFull_ThrowsException() {
		when(room.isFull()).thenReturn(true);

		assertThatThrownBy(() -> roomValidator.validateRoomNotFull(room))
			.isInstanceOf(RoomIsFullException.class);
	}

	@Test
	@DisplayName("방 나가기 - 방이 가득 차지 않은 경우 성공")
	void validateRoomNotFull_WhenNotFull_DoesNotThrow() {
		when(room.isFull()).thenReturn(false);

		assertThatCode(() -> roomValidator.validateRoomNotFull(room)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("게임 시작 - 방이 꽉 차지 않으면 RoomIsNotFullException")
	void validateRoomIsFull_WhenNotFull_ThrowsException() {
		when(room.isFull()).thenReturn(false);

		assertThatThrownBy(() -> roomValidator.validateRoomIsFull(room))
			.isInstanceOf(RoomIsNotFullException.class);
	}

	@Test
	@DisplayName("호스트가 아닌 사용자가 게임을 시작하면 OnlyHostCanStartGameException")
	void validateHost_WhenUserIsNotHost_ThrowsException() {
		when(room.isHost(1L)).thenReturn(false);

		assertThatThrownBy(() -> roomValidator.validateHost(room, 1L))
			.isInstanceOf(OnlyHostCanStartGameException.class);
	}

	@Test
	@DisplayName("호스트가 게임을 시작하면 예외 발생하지 않음")
	void validateHost_WhenUserIsHost_DoesNotThrow() {
		when(room.isHost(1L)).thenReturn(true);

		assertThatCode(() -> roomValidator.validateHost(room, 1L)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("방이 대기 상태가 아니라면 RoomNotWaitingException")
	void validateRoomWaiting_WhenNotWaiting_ThrowsException() {
		when(room.isWaiting()).thenReturn(false);

		assertThatThrownBy(() -> roomValidator.validateRoomWaiting(room))
			.isInstanceOf(RoomNotWaitingException.class);
	}

	@Test
	@DisplayName("방이 대기 상태라면 예외 발생하지 않음")
	void validateRoomWaiting_WhenWaiting_DoesNotThrow() {
		when(room.isWaiting()).thenReturn(true);

		assertThatCode(() -> roomValidator.validateRoomWaiting(room)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("팀 변경이 허용되지 않으면 TeamSwitchNotAllowed")
	void validateCanSwitchTeam_WhenNotAllowed_ThrowsException() {
		when(room.canSwitchTeam(Team.RED)).thenReturn(false);

		assertThatThrownBy(() -> roomValidator.validateCanSwitchTeam(room, Team.RED))
			.isInstanceOf(TeamSwitchNotAllowed.class);
	}

	@Test
	@DisplayName("팀 변경이 허용되면 예외 발생하지 않음")
	void validateCanSwitchTeam_WhenAllowed_DoesNotThrow() {
		when(room.canSwitchTeam(Team.RED)).thenReturn(true);

		assertThatCode(() -> roomValidator.validateCanSwitchTeam(room, Team.RED)).doesNotThrowAnyException();
	}

}