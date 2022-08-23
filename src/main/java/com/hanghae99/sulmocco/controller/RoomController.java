package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.EnterUserResponseDto;
import com.hanghae99.sulmocco.dto.RoomRequestDto;
import com.hanghae99.sulmocco.dto.RoomResponseDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.RedisRepository;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/chat")
public class RoomController {
    private final RoomService roomService;
    private final RedisRepository repository;


//    //방 조회
//    미사용 API
//    @GetMapping("/rooms")
//    @ResponseBody
//    public ResponseEntity<List<RoomResponseDto>> room() {
//        return ResponseEntity.ok().body(roomService.getRooms());
//    }

    //  방 조회 페이지 처리(무한스크롤)
    @GetMapping("/roomsscroll")
    @ResponseBody
    public ResponseEntity<List<RoomResponseDto>> roomscrooll(@RequestParam("page") int page,
                                                             @RequestParam("size") int size

    ) {
        page = page - 1;
        return ResponseEntity.ok().body(roomService.roomscroll(page, size));
    }


    //방 생성
    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody RoomRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok().body(roomService.createRoom(requestDto, user));
    }

    //  userEnter 테이블 조인(현재 방에 접속 중인 유저 확인 테이블)
    @PostMapping("/room/enter/{chatRoomId}")
    @ResponseBody
    public ResponseEntity<List<EnterUserResponseDto>> enterRoom(@PathVariable  String chatRoomId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(roomService.enterRoom(chatRoomId, userDetails.getUser()));
    }


    // enterUser 삭제
    @DeleteMapping("/room/quit/{chatRoomId}")
    @ResponseBody
    public void quitRoom(@PathVariable String chatRoomId,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        roomService.quitRoom(chatRoomId, user);
    }

    //특정방 조회
    @GetMapping("/room/{chatRoomId}")
    @ResponseBody
    public ResponseEntity<?> getRoomDetail(@PathVariable String chatRoomId) {
        return ResponseEntity.ok().body(roomService.getRoom(chatRoomId));
    }

//    //    운동중 true, 종료시 false
//    @PutMapping("/room/workout")
//    public void workout(@RequestBody RoomRequestDto requestDto,
//                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        roomService.workout(requestDto);
//    }

    //    room 삭제
    @DeleteMapping("/room/delete/{chatRoomId}")
    public void deleteRoom(@PathVariable String chatRoomId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        roomService.deleteRoom(chatRoomId, user);
    }
}

//
//    //룸 이름 조회
//    @PostMapping("/room/roomcheck")
//    @ResponseBody
//    public ResponseEntity<Boolean> roomCheck(@RequestBody RoomCheckRequestDto roomCheckRequestDto,
//                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        return ResponseEntity.ok().body(roomService.roomCheck(roomCheckRequestDto, user));
//    }
//}