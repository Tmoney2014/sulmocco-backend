package com.hanghae99.sulmocco.controller;

<<<<<<< HEAD
import com.hanghae99.sulmocco.dto.RoomUrlRequestDto;
import com.hanghae99.sulmocco.dto.RoomRequestDto;
import com.hanghae99.sulmocco.model.User;
=======
import com.hanghae99.sulmocco.dto.EnterUserResponseDto;
import com.hanghae99.sulmocco.dto.RoomRequestDto;
import com.hanghae99.sulmocco.dto.RoomResponseDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.RedisRepository;
>>>>>>> e8c9964fa9c806ac52147a97dc59258c126246ed
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
<<<<<<< HEAD
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
=======
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
>>>>>>> e8c9964fa9c806ac52147a97dc59258c126246ed
