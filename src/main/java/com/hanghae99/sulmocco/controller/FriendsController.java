package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.ReplyRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FriendsController {

    private final ReplyService replyService;

    // 친구목록
    @GetMapping("/api/friends/")
    public ResponseEntity<?> getFriends(@PathVariable Long tableId) {
        return friendsService.getFriends(tableId);
    }

    //친구추가
    @PostMapping("/api/friends/{username}")
    public ResponseEntity<?> createReply(@PathVariable Long tableId,
                                         @RequestBody ReplyRequestDto replyRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return replyService.createReply(tableId, replyRequestDto, user);
        }
        return ResponseEntity.badRequest().body("로그인이 만료되었습니다.");
    }

    // 친구 삭제
    @DeleteMapping("/api/friends/{username}")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return replyService.deleteReply(replyId, user);
        }
        return ResponseEntity.badRequest().body("로그인이 만료되었습니다.");
    }
}
