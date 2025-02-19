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
import prography.pingpong_game.room.exception.RoomNotFoundException;
import prography.pingpong_game.room.exception.UserNotInRoomException;
import prography.pingpong_game.room.repository.RoomRepository;
import prography.pingpong_game.room.repository.UserRoomRepository;
import prography.pingpong_game.user.entity.User;
import prography.pingpong_game.user.service.UserService;


@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private final UserService userService;
    private final RoomValidator roomValidator;
    @Transactional
    public void createRoom(RoomCreateRequest roomCreateRequest) {
        Long userId = roomCreateRequest.userId();
        User user = userService.getActiveUser(userId);
        roomValidator.validateUserNotInAnyRoom(userId);
        Long roomId = createNewRoom(roomCreateRequest, user);
        Room room = findRoom(roomId);
        addUserToRoom(room, user);
    }

    private Long createNewRoom(RoomCreateRequest roomCreateRequest, User user) {
        RoomType roomType = RoomType.fromString(roomCreateRequest.roomType());
        Room room = roomRepository.save(Room.create(roomCreateRequest.title(), user, roomType));
        return room.getId();
    }

    @Transactional(readOnly = true)
    public RoomDetailResponse findRoomDetail(Long roomId) {
        Room room = findRoom(roomId);
        return RoomDetailResponse.from(room);
    }

    private Room findRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(ApiStatus.BAD_REQUEST));
        return room;
    }

    @Transactional(readOnly = true)
    public RoomPageResponse findAllRooms(int size, int page) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAllRooms(pageable);
        return RoomPageResponse.from(roomPage);
    }

    @Transactional
    public void attentionRoom(Long roomId, AttendRequest attendRequest) {
        Room room = findRoom(roomId);
        User user = userService.getActiveUser(attendRequest.userId());
        roomValidator.validateRoomWaiting(room);
        roomValidator.validateRoomNotFull(room);
        roomValidator.validateUserNotInAnyRoom(attendRequest.userId());

        addUserToRoom(room, user);
    }

    private void addUserToRoom(Room room, User user) {
        Team team = room.assignTeam();
        UserRoom userRoom = UserRoom.create(room, user, team);
        userRoomRepository.save(userRoom);
        room.addUser(userRoom);
    }

    @Transactional
    public void outRoom(Long roomId, OutRoomRequest outRoomRequest) {
        Long userId = outRoomRequest.userId();
        Room room = findRoom(roomId);
        UserRoom userRoom = findUserRoom(roomId, userId);
        roomValidator.validateCanExitRoom(room);

        if (room.isHost(userId)) {
            handleHostExit(roomId, room);
        } else {
            handleUserExit(userRoom, room);
        }
    }

    private void handleHostExit(Long roomId, Room room) {
        userRoomRepository.deleteAllByRoomId(roomId);
        room.finish();
    }

    private void handleUserExit(UserRoom userRoom, Room room) {
        userRoomRepository.delete(userRoom);
        room.removeUser(userRoom);
    }

    private UserRoom findUserRoom(Long roomId, Long userId) {
        UserRoom userRoom = userRoomRepository.findUserRoom(userId, roomId)
                .orElseThrow(() -> new UserNotInRoomException(ApiStatus.BAD_REQUEST));
        return userRoom;
    }
}
