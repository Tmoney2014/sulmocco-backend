package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.repository.RedisRepository;
import com.hanghae99.sulmocco.security.jwt.JwtDecoder;
import com.hanghae99.sulmocco.service.ChatService;
import com.hanghae99.sulmocco.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisRepository redisRepository;
    private final ChatService chatService;
//    private final ChatMessageRepository chatMessageRepository;
    private final JwtDecoder jwtDecoder;


    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("Authorization") String token)
            throws InterruptedException {

        token = token.substring(7);
        message.setUserCount(redisRepository.getUserCount(message.getChatRoomId()));
//        chatMessageRepository.save(message);
        chatService.sendChatMessage(message);

    }
}