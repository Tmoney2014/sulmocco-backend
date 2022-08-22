package com.hanghae99.sulmocco.dto;

import com.hanghae99.sulmocco.model.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReplyResponseDto {

    private Long replyId;
    private String username;
    private String content;
    private LocalDateTime createdAt;

    public ReplyResponseDto(Reply reply) {
        this.replyId = reply.getId();
        this.username = reply.getUser().getUsername();
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedAt();
    }
    
}
