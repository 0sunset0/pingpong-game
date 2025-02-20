package prography.pingpong_game.common.event;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import prography.pingpong_game.room.service.RoomService;
import prography.pingpong_game.room.service.UserRoomService;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameEventListener {
	private final RoomService roomService;
	private final UserRoomService userRoomService;
	private final EntityManager entityManager;

	//TODO : 공부 하기
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void startGame(GameStartEvent gameStartEvent) {
		try {
			log.info("게임 종료");
//			finishGame(gameStartEvent);
		} catch (Exception e) {
			log.error("게임 종료 처리 중 오류 발생", e);
		}
	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public void finishGame(GameStartEvent gameStartEvent) {
//		Room updatedRoom = roomService.findRoomWithCapacity(gameStartEvent.roomId());
//		updatedRoom.finishGame();
//		userRoomService.deleteAllUserRooms(updatedRoom.getId());
//		log.info("게임이 종료되었습니다");
//	}
}
