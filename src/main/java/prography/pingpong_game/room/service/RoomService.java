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
        Long roomId = createNewRoom(roomCreateRequest, user);
        Room room = findRoom(roomId);
        addUserToRoom(room, user);
    }

    private User findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ApiStatus.BAD_REQUEST));
        return user;
    }

    private void validateUserActive(User user) {
        user.validateActive();
    }

    private Long createNewRoom(RoomCreateRequest roomCreateRequest, User user) {
        RoomType roomType = RoomType.fromString(roomCreateRequest.roomType());
        Room room = roomRepository.save(Room.create(roomCreateRequest.title(), user, roomType));
        return room.getId();
    }

    private void validateUserNotInRoom(Long userId) {
        System.out.println("userId = " + userId);
        boolean hasExistingRoom = userRoomRepository.existsActiveRoomByUserId(userId);
        if (hasExistingRoom) {
            throw new UserAlreadyInRoomException(ApiStatus.BAD_REQUEST);
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
        Room room = findRoom(roomId);
        User user = findUser(attendRequest.userId());
        validateRoomWaiting(room);
        validateRoomNotFull(room);
        validateUserActive(user);
        validateUserNotInRoom(attendRequest.userId());

        addUserToRoom(room, user);
    }

    private void addUserToRoom(Room room, User user) {
        Team team = room.assignTeam();
        UserRoom userRoom = UserRoom.create(room, user, team);
        userRoomRepository.save(userRoom);
        room.addUser(team);
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
