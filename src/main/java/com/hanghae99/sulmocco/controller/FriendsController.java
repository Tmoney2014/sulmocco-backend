package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.ReplyRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.FriendsService;
import com.hanghae99.sulmocco.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
public class FriendsController {

//    private final ReplyService replyService;

    private final FriendsService friendsService;

    // 친구목록
    @GetMapping("/api/friends/")
    public ResponseEntity<?> getFriends(@PathVariable Long tableId) {
        return friendsService.getFriends(tableId);
    }

    //친구추가
    @PostMapping("/api/friends/{username}")
    public ResponseEntity<?> createFriens(@PathVariable String username,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return friendsService.createFriends(username, user);
        }
        return ResponseEntity.badRequest().body("로그인이 만료되었습니다.");
    }

    // 친구 삭제
    @DeleteMapping("/api/friends/{username}")
    public ResponseEntity<?> deleteFriends(@PathVariable String username,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return friendsService.deleteFriends(username, user);
        }
        return ResponseEntity.badRequest().body("로그인이 만료되었습니다.");
    }
}
