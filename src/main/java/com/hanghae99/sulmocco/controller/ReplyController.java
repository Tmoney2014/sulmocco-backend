package com.hanghae99.sulmocco.controller;

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
    @GetMapping("/api/tables/{tableId}/replies")
    public ResponseEntity<?> getReplies(@PathVariable Long tableId) {
        return replyService.getReplies(tableId);
    }

    // 댓글 작성
    @PostMapping("/api/tables/{tableId}/replies")
    public ResponseEntity<?> createReply(@PathVariable Long tableId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return replyService.createReply(tableId);
        }
        return ResponseEntity.badRequest().build();
    }

    // 댓글 수정
    @PutMapping("/api/tables/{tableId}/replies/{replyId}")
    public ResponseEntity<?> updateReply(@PathVariable Long tableId,
                                         @PathVariable Long replyId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return replyService.updateReply(tableId, replyId);
        }
        return ResponseEntity.badRequest().build();
    }
    // 댓글 삭제
    @DeleteMapping("/api/tables/{tableId}/replies/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable Long tableId,
                                         @PathVariable Long replyId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return replyService.deleteReply(tableId, replyId);
        }
        return ResponseEntity.badRequest().build();
    }
}
