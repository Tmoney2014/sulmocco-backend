package com.hanghae99.sulmocco.websocket;

import com.hanghae99.sulmocco.model.EnterUser;
import com.hanghae99.sulmocco.model.Room;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.EnterUserRepository;
import com.hanghae99.sulmocco.repository.RedisRepository;
import com.hanghae99.sulmocco.repository.RoomRepository;
import com.hanghae99.sulmocco.repository.UserRepository;
import com.hanghae99.sulmocco.security.jwt.JwtDecoder;

import com.hanghae99.sulmocco.service.RoomService;
import com.hanghae99.sulmocco.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;
    private final ChatService chatService;
    private final RedisRepository redisRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomService roomService;


    private final Long min = 0L;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // 커넥트 커멘드 일시 헤더의 jwt token 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            jwtDecoder.decodeNickname(accessor.getFirstNativeHeader("Authorization").substring(7));

            //구독 커멘드 일시
        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {

            String destination = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId");
//            String chatRoomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
            String chatRoomId = chatService.getChatRoomId(destination);
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            log.info("message header 정보들={}", message.getHeaders());
            log.info("message destination은={}", destination);

            redisRepository.setUserEnterInfo(sessionId, chatRoomId);
            redisRepository.plusUserCount(chatRoomId);
//            String name = jwtDecoder.decodeNickname(accessor.getFirstNativeHeader("Authorization").substring(7));
            String username = jwtDecoder.decodeNickname(accessor.getFirstNativeHeader("Authorization").substring(7));

            redisRepository.setUsername(sessionId, username);
            try {
                chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.ENTER).chatRoomId(chatRoomId).sender(username).build());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (chatRoomId != null) {
                Room room = roomRepository.findByChatRoomId(chatRoomId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
                room.setUserCount(redisRepository.getUserCount(chatRoomId));
                if (redisRepository.getUserCount(chatRoomId) < 0 || room.getUserCount() < 0) { //입장유저가 -보다 작을때 0으로 셋팅
                    room.setUserCount(min);
                }
                roomRepository.save(room);  //입장한 유저 를 더해서 room에 저장.
            }

            //디스 커넥트 커맨드 일시
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            String chatRoomId = redisRepository.getUserEnterRoomId(sessionId);
            String username = redisRepository.getUsername(sessionId);

            if (chatRoomId != null) {
                redisRepository.minusUserCount(chatRoomId);
                try {
                    chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.QUIT).chatRoomId(chatRoomId).sender(username).build());
                    log.info("유저네임 ={}", username);
                    log.info("챗룸아이디 ={}", chatRoomId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Room room = roomRepository.findByChatRoomId(chatRoomId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다.(DISCONNECT)"));
                room.setUserCount(redisRepository.getUserCount(chatRoomId));
                if (redisRepository.getUserCount(chatRoomId) < 0 || room.getUserCount() < 0) { //입장유저가 -보다 작을때 0으로 셋팅
                    room.setUserCount(min);
                }
                roomRepository.save(room);  //퇴장한 유저 를 빼서 room에 저장.

                //quit room
                User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저네임입니다"));
                roomService.quitRoom(chatRoomId, user);


                redisRepository.removeUserEnterInfo(sessionId);
                log.info("DISCONNECTED {}, {}", sessionId, chatRoomId);
            }
        }
        return message;
    }
}
