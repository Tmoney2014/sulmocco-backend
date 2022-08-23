package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.RoomUrlRequestDto;
import com.hanghae99.sulmocco.dto.RoomRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // 술모임 검색
    @GetMapping("/api/rooms/search")
    public ResponseEntity<?> getPagingRoomsBySearch(@RequestParam("page") int page,
                                                    @RequestParam("size") int size,
                                                    @RequestParam("sortBy") String sortBy,
                                                    @RequestParam("isAsc") boolean isAsc,
                                                    @RequestParam(value = "keyword", required = false) String keyword) {
        page = page - 1;
        return roomService.getPagingRoomsBySearch(page, size, sortBy, isAsc, keyword);
    }

    // 술모임 목록
    @GetMapping("/api/rooms")
    public ResponseEntity<?> getPagingRooms(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("sortBy") String sortBy,
                                            @RequestParam("isAsc") boolean isAsc,
                                            @RequestParam(value = "alcohol", required = false) String alcohol) {
        page = page - 1;
        return roomService.getPagingRooms(page, size, sortBy, isAsc, alcohol);
    }

    // 술모임 생성
    @PostMapping("/api/room")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return roomService.createRoom(requestDto, user);
    }

    // 술모임 입장
    @PostMapping("/api/room/enter/{roomId}")
    public ResponseEntity<?> enterRoom(@PathVariable String roomId,
                                       @RequestBody RoomUrlRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return roomService.enterRoom(roomId, requestDto, user);
    }

    // 지금 인기있는 술약속 Top 8
    @GetMapping("/api/room/main")
    public ResponseEntity<?> getRoomsOrderByCount() {
        return roomService.getRoomsOrderByCount();
    }

    // 술모임 끝
    @DeleteMapping("/api/room/delete/{roomId}")
    public ResponseEntity<?> deleteRoom(@RequestParam String roomId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return roomService.deleteRoom(roomId, user);
    }

}
