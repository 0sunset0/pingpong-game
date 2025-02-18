package prography.pingpong_game.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong_game.common.exception.ApiStatus;
import prography.pingpong_game.room.dto.request.AttendRequest;
import prography.pingpong_game.room.dto.request.OutRoomRequest;
import prography.pingpong_game.room.dto.response.RoomDetailResponse;
import prography.pingpong_game.room.dto.request.RoomCreateRequest;
import prography.pingpong_game.room.dto.response.RoomPageResponse;
import prography.pingpong_game.room.entity.*;
import prography.pingpong_game.room.exception.RoomCapacityExceededException;
import prography.pingpong_game.room.exception.RoomNotFoundException;
import prography.pingpong_game.room.exception.RoomNotWaitingException;
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
        User user = findUser(userId);
        validateUserActive(user);
        validateUserNotInRoom(userId);
        createNewRoom(roomCreateRequest, user);
        //TODO : 방 생성 후 호스트를 방에 참여시켜야 함.
    }

    private User findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ApiStatus.BAD_REQUEST));
        return user;
    }

    private void validateUserActive(User user) {
        user.validateActive();
    }

    private void createNewRoom(RoomCreateRequest roomCreateRequest, User user) {
        RoomType roomType = RoomType.fromString(roomCreateRequest.roomType());
        roomRepository.save(Room.create(roomCreateRequest.title(), user, roomType));
    }

    private void validateUserNotInRoom(Long userId) {
        boolean hasExistingRoom = userRoomRepository.existsActiveRoomByUserId(userId);
        if (hasExistingRoom) {
            new UserAlreadyInRoomException(ApiStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public RoomDetailResponse findRoomDetail(Long roomId) {
        Room room = findRoom(roomId);
        return RoomDetailResponse.from(room);
    }

    private Room findRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(ApiStatus.BAD_REQUEST));
        return room;
    }

    @Transactional
    public RoomPageResponse findAllRooms(int size, int page) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAllRooms(pageable);
        return RoomPageResponse.from(roomPage);
    }
    @Transactional
    public void attentionRoom(Long roomId, AttendRequest attendRequest) {
        //방이 대기 상태인지 확인
        Room room = findRoom(roomId);
        validateRoomWaiting(room);
        //참여하고자 하는 방이 정원 미달인지 확인
        validateRoomNotFull(room);

        //유저 활성 상태인지 확인
        Long userId = attendRequest.userId();
        User user = findUser(userId);
        validateUserActive(user);

        //유저가 현재 참여한 방이 있는지 확인
        validateUserNotInRoom(userId);

        //참가 팀 결정 (레드가 비었으면 레드 먼저)
        Team team = room.getRoomCapacity().assignTeam();

        //유저룸 생성 후 저장 -> 유저가 생성되면,, 룸 정원이 올라가야 함
        UserRoom userRoom = UserRoom.create(room, user, team);
        userRoomRepository.save(userRoom);
        room.getRoomCapacity().addUserToTeam(team);
    }

    private static void validateRoomNotFull(Room room) {
        if (room.isFull()) {
            throw new RoomCapacityExceededException(ApiStatus.BAD_REQUEST);
        }
    }

    private static void validateRoomWaiting(Room room) {
        if (!room.isWaiting()) {
            throw new RoomNotWaitingException(ApiStatus.BAD_REQUEST);
        }
    }

    public Object outRoom(Long roomId, OutRoomRequest outRoomRequest) {
        return null;
    }
}
