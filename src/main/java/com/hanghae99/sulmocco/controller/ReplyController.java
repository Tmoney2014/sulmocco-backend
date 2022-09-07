package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.reply.ReplyRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 목록
    @GetMapping("/api/replies/{tableId}")
    public ResponseEntity<?> getReplies(@PathVariable Long tableId) {
        return replyService.getReplies(tableId);
    }

    // 댓글 작성
    @PostMapping("/api/replies/{tableId}")
    public ResponseEntity<?> createReply(@PathVariable Long tableId,
                                         @RequestBody ReplyRequestDto replyRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return replyService.createReply(tableId, replyRequestDto, user);
        }
        return ResponseEntity.badRequest().body("로그인이 만료되었습니다.");
    }

    // 댓글 수정
    @PutMapping("/api/replies/{replyId}")
    public ResponseEntity<?> updateReply(@PathVariable Long replyId,
                                         @RequestBody ReplyRequestDto replyRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return replyService.updateReply(replyId, replyRequestDto, user);
        }
        return ResponseEntity.badRequest().body("로그인이 만료되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/api/replies/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return replyService.deleteReply(replyId, user);
        }
        return ResponseEntity.badRequest().body("로그인이 만료되었습니다.");
    }
}
