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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final EnterUserRepository enterUserRepository;
    private final int LIMIT = 500;

    /**
     * 술모임 만들기
     */
    public String createRoom(RoomRequestDto requestDto, User user) {

        if (requestDto.getTitle() == null) {
            throw new IllegalArgumentException("방 이름을 입력해주세요.");
        }
        if (requestDto.getThumbnail() == null) {
            requestDto.setThumbnail("Default 이미지URL");
        }

        Room room = Room.create(requestDto, user);
        //비밀번호가 있다면 true, 없다면 false
        Room createRoom = roomRepository.save(room);

        return createRoom.getChatRoomId();
    }

    /**
     * 술모임 입장
     */
    //방 진입
    public List<EnterUserResponseDto> enterRoom(String chatRoomId, User user) {

        Room room = roomRepository.findByChatRoomId(chatRoomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        EnterUser enterCheck = enterUserRepository.findByRoomAndUser(room, user);

        if (enterCheck != null) {
            throw new IllegalArgumentException("이미 입장한 방입니다.");
        }

        List<EnterUser> enterUserSize = enterUserRepository.findByRoom(room);

        if (enterUserSize.size() > 0) {
            if (LIMIT < enterUserSize.size() + 1) {
                throw new IllegalArgumentException("입장인원을 초과하였습니다.");
            }
        }

        EnterUser enterUser = new EnterUser(user, room);
        enterUserRepository.save(enterUser);

        List<EnterUser> enterUsers = enterUserRepository.findByRoom(room);
        List<EnterUserResponseDto> enterRoomUsers = new ArrayList<>();
        for (EnterUser enterUser2 : enterUsers) {
            enterRoomUsers.add(new EnterUserResponseDto(
                    enterUser2.getUser().getUsername(),
                    enterUser2.getUser().getProfileUrl()
            ));
        }
        return enterRoomUsers;
    }

    /**
     * 술모임 나가기
     */
    public void quitRoom(String roomId, User user) {
        Room room = roomRepository.findByChatRoomId(roomId).orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));
        EnterUser enterUser = enterUserRepository.findByRoomAndUser(room, user);
        enterUserRepository.delete(enterUser);
    }

    /**
     * 술모임 종료
     */
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
        List<Room> hotRooms = roomRepository.findByOrderByCount(pageable);

        List<RoomResponseDto> hotRoomsDtos = new ArrayList<>();
        for (Room hotRoom : hotRooms) {
            hotRoomsDtos.add(new RoomResponseDto(hotRoom));
        }
        return ResponseEntity.ok().body(hotRoomsDtos);
    }

    /**
     * 전체 술모임 검색
     */
    public ResponseEntity<?> getPagingRoomsBySearch(int page, int size, String sortBy, boolean isAsc, String keyword) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        // sortBy : count(인기순) / id (최신순)
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Slice<Room> roomSlice = roomRepository.getRoomsBySearch(pageable, keyword);

        Slice<RoomResponseDto> roomResponseDtos = RoomResponseDto.roomList(roomSlice);

        return ResponseEntity.ok().body(roomResponseDtos);
    }

    /**
     * 전체 술모임 조회
     */
    public ResponseEntity<?> getPagingRooms(int page, int size, String sortBy, boolean isAsc, String alcohol) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Slice<Room> roomSlice = null;

        if (alcohol == null) {
            roomSlice = roomRepository.findAllRooms(pageable); // 전체 목록
        } else {
            String[] splitAlcoholTag = alcohol.split(",");  // 소주,맥주,와인
            roomSlice = roomRepository.getRoomsOrderByAlcoholTag(pageable, splitAlcoholTag);
        }

        Slice<RoomResponseDto> roomResponseDtos = RoomResponseDto.roomList(roomSlice);

        return ResponseEntity.ok().body(roomResponseDtos);
    }

    //특정 방 조회
    public ResponseEntity<?> getRoom(String chatRoomId) {
        Room room = roomRepository.findByChatRoomId(chatRoomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        return new ResponseEntity<>(new RoomResponseDto(room), HttpStatus.valueOf(200));
    }
}
