package prography.pingpong_game.common.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import prography.pingpong_game.room.service.RoomService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableAsync
@EnableScheduling
public class GameEventListener {
	private final RoomService roomService;
	private final TaskScheduler taskScheduler;
	private static final int GAME_DURATION_SECONDS = 60;
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void startGame(GameStartEvent gameStartEvent) {
		log.info("게임 시작 : roomId = {}", gameStartEvent.roomId());
		scheduleFinishGame(gameStartEvent.roomId());
	}

	@Async
	public void scheduleFinishGame(Long roomId) {
		taskScheduler.schedule(
				() -> finishGame(roomId),
				Instant.now().plusSeconds(GAME_DURATION_SECONDS)
		);
	}
	private void finishGame(Long roomId) {
		log.info("게임 종료");
		roomService.finishGame(roomId);
	}
}
