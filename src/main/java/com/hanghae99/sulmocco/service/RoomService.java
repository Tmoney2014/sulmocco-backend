package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.*;
import com.hanghae99.sulmocco.model.EnterUser;
import com.hanghae99.sulmocco.model.Room;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.EnterUserRepository;
import com.hanghae99.sulmocco.repository.RedisRepository;
import com.hanghae99.sulmocco.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final EnterUserRepository enterUserRepository;

    private final int FriendsLimit = 6;

    private final int HostLimit = 500;

    /**
     * 술모임 만들기
     */
    @Transactional
    public String createRoom(RoomRequestDto requestDto, User user) {

        Optional<Room> findRoom = roomRepository.findByUsername(user.getUsername());
        if (findRoom.isPresent()) {
            throw new IllegalArgumentException("술모임은 하나만 생성할 수 있습니다.");
        }

        if (requestDto.getTitle() == null) {
            throw new IllegalArgumentException("방 이름을 입력해주세요.");
        }
//        if (requestDto.getThumbnail() == null) {
//            requestDto.setThumbnail("Default 이미지URL");
//        }

        Room room = Room.create(requestDto, user);
        Room createRoom = roomRepository.save(room);

        return createRoom.getChatRoomId();
    }

    /**
     * 술모임 입장
     */
    //방 진입
    @Transactional
    public ResponseEntity<?> enterRoom(String chatRoomId, User user) {

        Room room = roomRepository.findByChatRoomId(chatRoomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        EnterUser enterCheck = enterUserRepository.findByRoomAndUser(room, user);

        if (enterCheck != null) {
            throw new IllegalArgumentException("이미 입장한 방입니다.");
        }

        List<EnterUser> enterUserSize = enterUserRepository.findByRoom(room);


        if (room.getVersion().contains("friend")) {
            if (enterUserSize.size() > 0) {
                if (FriendsLimit < enterUserSize.size() + 1) {
                    throw new IllegalArgumentException("입장인원을 초과하였습니다. (6)");
                }
            }
        } else if (enterUserSize.size() > 0) {
            if (HostLimit < enterUserSize.size() + 1) {
                throw new IllegalArgumentException("입장인원을 초과하였습니다. (500)");
            }
        }

        EnterUser enterUser = new EnterUser(user, room);
        enterUserRepository.save(enterUser);

        return new ResponseEntity<>(new RoomResponseDto(room), HttpStatus.valueOf(200));
    }

//        List<EnterUser> enterUsers = enterUserRepository.findByRoom(room);
//        List<EnterUserResponseDto> enterRoomUsers = new ArrayList<>();
//        for (EnterUser enterUser2 : enterUsers) {
//            enterRoomUsers.add(new EnterUserResponseDto(
//                    enterUser2.getUser().getUsername(),
//                    enterUser2.getUser().getProfileUrl()
//            ));
//    }
//        return enterRoomUsers;
//    }

    /**
     * 술모임 나가기
     */
    @Transactional
    public void quitRoom(String chatRoomId, User user) {
        Room room = roomRepository.findByChatRoomId(chatRoomId).orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        EnterUser enterUser = enterUserRepository.findByRoomAndUser(room, user);
        enterUserRepository.delete(enterUser);
    }

    /**
     * 술모임 종료
     */
    @Transactional
    public ResponseEntity<?> deleteRoom(String roomId, User user) {

        Room room = roomRepository.findByChatRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        if (!room.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("방을 만든 유저만 삭제할 수 있습니다.");
        }

//        if (enterUserRepository.findByRoom(room) != null) {
//            throw new IllegalArgumentException("모든 유저가 퇴장 후 방을 삭제할 수 있습니다.");
//        }
        roomRepository.delete(room);

        return ResponseEntity.ok().body(new ResponseDto(true, "오늘 술은 여기까지입니다."));
    }

    /**
     * 지금 인기있는 술약속 Top 8
     */
    public ResponseEntity<?> getRoomsOrderByCount() {

        Pageable pageable = PageRequest.ofSize(8);
        String[] setVersion = {"host", "friend"};
        List<Room> hotRooms = roomRepository.findByOrderByCount(pageable, setVersion);

        List<RoomResponseDto> hotRoomsDtos = new ArrayList<>();
        for (Room hotRoom : hotRooms) {
            hotRoomsDtos.add(new RoomResponseDto(hotRoom));
        }
        return ResponseEntity.ok().body(hotRoomsDtos);
    }

    /**
     * 전체 술모임 검색
     */
    public ResponseEntity<?> getPagingRoomsBySearch(int page, int size, String sortBy, boolean isAsc, String keyword, String version) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        // sortBy : count(인기순) / id (최신순)
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Slice<Room> roomSlice = null;
        String[] setVersion = {"host", "friend"};

        if (version == null) {
            roomSlice = roomRepository.getRoomsBySearch(pageable, keyword, setVersion);
        } else {
            roomSlice = roomRepository.getRoomsByVersionAndSearch(pageable, keyword, version);
        }

        Slice<RoomResponseDto> roomResponseDtos = RoomResponseDto.roomList(roomSlice);

        return ResponseEntity.ok().body(roomResponseDtos);
    }

    /**
     * 전체 술모임 조회
     */
    public ResponseEntity<?> getPagingRooms(int page, int size, String sortBy, boolean isAsc, String alcohol, String version) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        // sortBy : count(인기순) / id (최신순)
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Slice<Room> roomSlice = null;
        String[] setVersion = {"host", "friend"};

        if (version == null) {
            if (alcohol == null) {
                // version X 술태그 X (첫화면)
                roomSlice = roomRepository.findAllRooms(pageable, setVersion);
            } else {
                // version X 술태그 O
                String[] splitAlcoholTag = alcohol.split(",");  // ex) 소주,맥주,와인
                roomSlice = roomRepository.getRoomsOrderByAlcoholTag(pageable, splitAlcoholTag, setVersion);
            }
        } else {
            if (alcohol == null) {
                // version O 술태그 X
                roomSlice = roomRepository.findAllRoomsByVersion(pageable, version);
            } else {
                // version O 술태그 O
                String[] splitAlcoholTag = alcohol.split(",");  // ex) 소주,맥주,와인
                roomSlice = roomRepository.getRoomsOrderByVersionAndAlcoholTag(pageable, version, splitAlcoholTag);
            }
        }

        Slice<RoomResponseDto> roomResponseDtos = RoomResponseDto.roomList(roomSlice);

        return ResponseEntity.ok().body(roomResponseDtos);
    }
}

//    //특정 방 조회
//    @Transactional
//    public ResponseEntity<?> getRoom(String chatRoomId) {
//        Room room = roomRepository.findByChatRoomId(chatRoomId).orElseThrow(
//                () -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
//
//        return new ResponseEntity<>(new RoomResponseDto(room), HttpStatus.valueOf(200));
//    }
