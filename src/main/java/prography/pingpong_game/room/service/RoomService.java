package prography.pingpong_game.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.dto.response.RoomDetailResponse;
import prography.pingpong_game.room.dto.request.RoomCreateRequest;
import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.exception.RoomNotFoundException;
import prography.pingpong_game.room.repository.RoomRepository;
import prography.pingpong_game.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    /**
     * - userId, roomType, title 정보는 body에 담아서 요청합니다.
     * - 방을 생성하려고 하는 user(userId)의 상태가 활성(ACTIVE)상태일 때만, 방을 생성할 수 있습니다. 만약 활성상태가 아닐때는 201 응답을 반환합니다.
     * - 방을 생성하려고 하는 user(userId)가 현재 참여한 방이 있다면, 방을 생성할 수 없습니다. 만약 참여하고 있는 방이 있을때는 201 응답을 반환합니다.
     * - 방은 초기에 대기(WAIT) 상태로 생성됩니다.
     * - 데이터가 저장되는 시점에 따라 createdAt과 updatedAt을 저장합니다.
     */
    @Transactional
    public void createRoom(RoomCreateRequest roomCreateRequest) {
        //유저 활성 상태 확인
        //유저 현재 참여한 방 있는지 확인 (유저룸이 있는지)
        //방 생성

    }

    @Transactional
    public RoomDetailResponse findRoomDetail(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(ApiStatus.BAD_REQUEST));
        return RoomDetailResponse.from(room);
    }
}
