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
                                                    @RequestParam(value = "keyword", required = false) String keyword,
                                                    @RequestParam(value = "version", required = false) String version) {
        page = page - 1;
        return roomService.getPagingRoomsBySearch(page, size, sortBy, isAsc, keyword, version);
    }

    // 술모임 목록
    @GetMapping("/api/room")
    public ResponseEntity<?> getPagingRooms(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("sortBy") String sortBy,
                                            @RequestParam("isAsc") boolean isAsc,
                                            @RequestParam(value = "alcohol", required = false) String alcohol,
                                            @RequestParam(value = "version", required = false) String version) {
        page = page - 1;
        return roomService.getPagingRooms(page, size, sortBy, isAsc, alcohol, version);
    }

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
    //  TODO 필요한지 알아봐야함
    @PostMapping("/api/chat/room/enter/{chatRoomId}")
    public ResponseEntity<List<EnterUserResponseDto>> enterRoom(@PathVariable String chatRoomId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(roomService.enterRoom(chatRoomId, userDetails.getUser()));
    }

    // enterUser 삭제
    // TODO 필요한지 알아 봐야함
    @DeleteMapping("/api/chat/room/quit/{chatRoomId}")
    public void quitRoom(@PathVariable String chatRoomId,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        roomService.quitRoom(chatRoomId, user);
    }

    //술모임 상세
    @GetMapping("/api/chat/room/{chatRoomId}")
    public ResponseEntity<?> getRoomDetail(@PathVariable String chatRoomId) {
        return ResponseEntity.ok().body(roomService.getRoom(chatRoomId));
    }

    //방 삭제
    @DeleteMapping("/api/room/delete/{chatRoomId}")
    public void deleteRoom(@PathVariable String chatRoomId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        roomService.deleteRoom(chatRoomId, user);
    }
}

