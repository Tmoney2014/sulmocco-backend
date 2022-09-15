package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.reply.ReplyRequestDto;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.Reply;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.ReplyRepository;
import com.hanghae99.sulmocco.repository.TablesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @Mock
    ReplyRepository replyRepository;
    @Mock
    TablesRepository tablesRepository;
    ReplyService replyService;

    private User user1;
    private User user2;
    private Tables tables;
    private Reply reply1;

    @BeforeEach
    void setup() {

        user1 = User.builder()
                .userId(1L)
                .id("Table01")
                .username("testUser")
                .password("1111!!!!")
                .level("술찌")
                .profileUrl("default.jpg")
                .build();

        user2 = User.builder()
                .userId(2L)
                .id("Table02")
                .username("testUser2")
                .password("2222@@@@")
                .level("술찌")
                .profileUrl("default.jpg")
                .build();

        tables = Tables.builder()
                .id(1L)
                .title("개노잼")
                .content("이렇게 하는게 맞나")
                .alcoholTag("소주")
                .freeTag("자유")
                .thumbnailImgUrl("default.png")
                .imgUrls(new ArrayList<>())
                .user(user1)
                .replies(new ArrayList<>())
                .build();

        reply1 = Reply.builder()
                .id(1L)
                .content("코멘트1")
                .user(user2)
                .tables(tables)
                .build();

        replyService = new ReplyService(tablesRepository, replyRepository);
    }

    @Test
    @DisplayName("댓글 작성")
    @Order(1)
    void createReply() {

        /* given */
        given(tablesRepository.findById(anyLong())).willReturn((Optional.of(tables)));
        given(replyRepository.save(any(Reply.class))).willReturn(reply1);
        given(tablesRepository.findById(1L)).willReturn((Optional.of(tables)));

        ReplyRequestDto replyRequestDto = new ReplyRequestDto("testReply");

        /* when */
        ResponseEntity<?> result = replyService.createReply(tables.getId(), replyRequestDto, user1);

        /* then */
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("댓글 삭제")
    @Order(2)
    void deleteReply() {

        /* given */
        Long replyId = reply1.getId();
//        when(replyRepository.getById(1L)).thenReturn(Reply1);
        when(replyRepository.findById(1L)).thenReturn(Optional.of(reply1));

        /* when */
        ResponseEntity<?> result = replyService.deleteReply(replyId, user2);

        /* then */
//        then(replyRepository)
//                .should(times(1))
//                .deleteById(replyId);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("댓글 수정")
    @Order(3)
    void updateReply() {

        /* given */
        given(replyRepository.findById(anyLong())).willReturn((Optional.of(reply1)));
        ReplyRequestDto replyRequestDto = new ReplyRequestDto("updateReply");

        /* when */
        ResponseEntity<?> result = replyService.updateReply(reply1.getId(), replyRequestDto, user2);

        /* then */
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("댓글 목록")
    @Order(4)
    void replyList() {

        /* given */
        List<Reply> replies = new ArrayList<>();
        given(replyRepository.findAllByTablesOrderByCreatedAtDesc(tables)).willReturn(replies);
        given(tablesRepository.findById(anyLong())).willReturn((Optional.of(tables)));

        /* when */
        ResponseEntity<?> result = replyService.getReplies(tables.getId());

        /* then */
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}