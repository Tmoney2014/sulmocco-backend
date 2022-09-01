package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likesService;

    //좋아요 생성
    @PostMapping("/api/tables/{tableId}/like")
    public ResponseEntity<?> postLikes(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long tableId) {
        Long userId = userDetails.getUser().getUserId();
        return likesService.postLikes(userId, tableId);
    }

    //좋아요 삭제
    @DeleteMapping("/api/tables/{tableId}/like")
    public ResponseEntity<?> deletetLikes(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long tableId) {
        User user = userDetails.getUser();
        return likesService.deleteLikes(user, tableId);
    }
}
