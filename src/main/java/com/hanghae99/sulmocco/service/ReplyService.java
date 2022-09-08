package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.reply.ReplyRequestDto;
import com.hanghae99.sulmocco.dto.reply.ReplyResponseDto;
import com.hanghae99.sulmocco.dto.response.ResponseDto;
import com.hanghae99.sulmocco.model.Dish;
import com.hanghae99.sulmocco.model.Reply;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.ReplyRepository;
import com.hanghae99.sulmocco.repository.TablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final TablesRepository tablesRepository;
    private final ReplyRepository replyRepository;

    /**
     * 댓글 목록
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> getReplies(Long tableId) {

        // 술상 조회
        Dish findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));

        // 술상 댓글 DB 조회
        List<Reply> replies = replyRepository.findAllByDishOrderByCreatedAtDesc(findTable);

        List<ReplyResponseDto> replyResponseDtos = new ArrayList<>();
        for (Reply myReplies : replies) {
            ReplyResponseDto reviewResponseDto = new ReplyResponseDto(myReplies);
            replyResponseDtos.add(reviewResponseDto);
        }

        return ResponseEntity.ok().body(replyResponseDtos);
    }

    /**
     * 댓글 작성
     */
    public ResponseEntity<?> createReply(Long tableId, ReplyRequestDto replyRequestDto, User user) {
        // 술상 조회
        Dish findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));
        // 댓글 생성
        Reply reply = new Reply(replyRequestDto.getContent(), user, findTable);
        // 댓글 저장
        replyRepository.save(reply);

        return ResponseEntity.ok(new ResponseDto(true, "댓글이 등록되었습니다."));
    }

    /**
     * 댓글 수정
     */
    public ResponseEntity<?> updateReply(Long replyId, ReplyRequestDto replyRequestDto, User user) {
        // 댓글 조회
        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        // 댓글 작성자 확인
        if (!reply.getUser().getId().equals(user.getId()))
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");

        if (reply.getContent().equals(replyRequestDto.getContent())) {
            throw new IllegalArgumentException("내용이 변경되지 않았습니다.");
        }
        reply.update(replyRequestDto.getContent(), user); // 더티 체킹

        return ResponseEntity.ok(new ResponseDto(true, "댓글이 수정되었습니다."));
    }

    /**
     * 댓글 삭제
     */
    public ResponseEntity<?> deleteReply(Long replyId, User user) {

        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!reply.getUser().getId().equals(user.getId()))
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");

        replyRepository.delete(reply);
        return ResponseEntity.ok(new ResponseDto(true, "댓글이 삭제되었습니다."));
    }
}
