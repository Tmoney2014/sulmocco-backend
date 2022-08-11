package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;

    /**
     *  댓글 목록
     */
    public ResponseEntity<?> getReplies(Long tableId) {
        return null;
    }

    /**
     *  댓글 작성
     */
    public ResponseEntity<?> createReply(Long tableId) {
        return null;
    }

    /**
     *  댓글 수정
     */
    public ResponseEntity<?> updateReply(Long tableId, Long replyId) {
        return null;
    }

    /**
     *  댓글 삭제
     */
    public ResponseEntity<?> deleteReply(Long tableId, Long replyId) {
        return null;
    }
}
