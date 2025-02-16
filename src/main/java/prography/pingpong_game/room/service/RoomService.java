package prography.pingpong_game.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.dto.response.RoomDetailResponse;
import prography.pingpong_game.room.dto.request.RoomCreateRequest;
import prography.pingpong_game.room.dto.response.RoomPageResponse;
import prography.pingpong_game.room.entity.Room;
import prography.pingpong_game.room.entity.RoomType;
import prography.pingpong_game.room.exception.RoomNotFoundException;
import prography.pingpong_game.room.exception.UserAlreadyInRoomException;
import prography.pingpong_game.room.repository.RoomRepository;
import prography.pingpong_game.room.repository.UserRoomRepository;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.exception.UserNotFoundException;
import prography.pingpong_game.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    @Transactional
    public void createRoom(RoomCreateRequest roomCreateRequest) {
        Long userId = roomCreateRequest.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ApiStatus.BAD_REQUEST));
        validateUserActive(user);
        validateUserNotInRoom(userId);
        createNewRoom(roomCreateRequest, user);
    }

    private void validateUserActive(User user) {
        user.validateActive();
    }

    private void createNewRoom(RoomCreateRequest roomCreateRequest, User user) {
        RoomType roomType = RoomType.fromString(roomCreateRequest.roomType());
        roomRepository.save(Room.create(roomCreateRequest.title(), user, roomType));
    }

    private void validateUserNotInRoom(Long userId) {
        boolean hasExistingRoom = userRoomRepository.existsByUserId(userId);
        if (hasExistingRoom){
            new UserAlreadyInRoomException(ApiStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public RoomDetailResponse findRoomDetail(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(ApiStatus.BAD_REQUEST));
        return RoomDetailResponse.from(room);
    }

    public RoomPageResponse findAllRooms(int size, int page) {
        return null;
    }
}
