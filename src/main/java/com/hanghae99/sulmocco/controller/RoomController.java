package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.RoomRequestDto;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class RoomController {


   private final RoomService roomService;

//    //술약속 목록
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<Room> room() {
//        return chatRoomRepository.findAllRoom();
//    }
    // 방송을 시작 할때 채팅룸을 만든다.

    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody RoomRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        String profileurl = userDetails.getUser().getProfileUrl();
        return ResponseEntity.ok().body(roomService.createRoom(requestDto, username, profileurl));
    }

    // 다른 사용 자는 입장만 하면 된다. 술약속 상세 페이제 리스폰스로 chatRoomId 를 보내준다.
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("chatRoomId", roomId);
        return "/chat/roomdetail";
    }

//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public Room roomInfo(@PathVariable String roomId) {
//        return chatRoomRepository.findRoomById(roomId);
//    }
}
