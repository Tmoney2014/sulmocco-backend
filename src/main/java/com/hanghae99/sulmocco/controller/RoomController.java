package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.EnterUserResponseDto;
import com.hanghae99.sulmocco.dto.RoomRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // 술모임 검색
    @GetMapping("/api/room/search")
    public ResponseEntity<?> getPagingRoomsBySearch(@RequestParam("page") int page,
                                                    @RequestParam("size") int size,
                                                    @RequestParam("sortBy") String sortBy,
                                                    @RequestParam("isAsc") boolean isAsc,
                                                    @RequestParam(value = "keyword", required = false) String keyword) {
        page = page - 1;
        return roomService.getPagingRoomsBySearch(page, size, sortBy, isAsc, keyword);
    }

    // 술모임 목록
    @GetMapping("/api/room")
    public ResponseEntity<?> getPagingRooms(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("sortBy") String sortBy,
                                            @RequestParam("isAsc") boolean isAsc,
                                            @RequestParam(value = "alcohol", required = false) String alcohol) {
        page = page - 1;
        return roomService.getPagingRooms(page, size, sortBy, isAsc, alcohol);
    }

//    // 술모임 생성
//    @PostMapping("/api/room")
//    public ResponseEntity<?> createRoom(@RequestBody RoomRequestDto requestDto,
//                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        return roomService.createRoom(requestDto, user);
//    }
//
//    // 술모임 입장
//    @PostMapping("/api/room/enter/{roomId}")
//    public ResponseEntity<?> enterRoom(@PathVariable String roomId,
//                                       @RequestBody RoomUrlRequestDto requestDto,
//                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        return roomService.enterRoom(roomId, requestDto, user);
//    }

    // 지금 인기있는 술약속 Top 8
    @GetMapping("/api/room/main")
    public ResponseEntity<?> getRoomsOrderByCount() {
        return roomService.getRoomsOrderByCount();
    }

    //방 생성
    @PostMapping("/api/chat/room")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok().body(roomService.createRoom(requestDto, user));
    }

    //  userEnter 테이블 조인(현재 방에 접속 중인 유저 확인 테이블)
    @PostMapping("/api/chat/room/enter/{chatRoomId}")
    public ResponseEntity<List<EnterUserResponseDto>> enterRoom(@PathVariable  String chatRoomId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(roomService.enterRoom(chatRoomId, userDetails.getUser()));
    }

    // enterUser 삭제
    @DeleteMapping("/api/chat/room/quit/{chatRoomId}")
    public void quitRoom(@PathVariable String chatRoomId,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        roomService.quitRoom(chatRoomId, user);
    }

    //특정방 조회
    @GetMapping("/api/chat/room/{chatRoomId}")
    public ResponseEntity<?> getRoomDetail(@PathVariable String chatRoomId) {
        return ResponseEntity.ok().body(roomService.getRoom(chatRoomId));
    }

    //    room 삭제
    @DeleteMapping("/api/room/delete/{chatRoomId}")
    public void deleteRoom(@PathVariable String chatRoomId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        roomService.deleteRoom(chatRoomId, user);
    }
}

