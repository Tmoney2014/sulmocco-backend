package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.FriendsRequestDto;
import com.hanghae99.sulmocco.dto.FriendsResponseDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.model.Friends;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.FriendsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendsService {

    private final FriendsRepository friendsRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getFriends(Long friendsId) {

        //친구 조회
        Friends findFriends = friendsRepository.findById(friendsId).orElseThrow(
                () -> new IllegalArgumentException("친구가 존재하지 않습니다."));

        // 술상 댓글 DB 조회
        List<Friends> friends = friendsRepository.findAllByFriendsOrderByCreatedAtDesc(findFriends);

        List<FriendsResponseDto> friendsResponseDtos = new ArrayList<>();
        for (Friends myFriends : friends) {
            FriendsResponseDto friendsResponseDto = new FriendsResponseDto(myFriends);
            friendsResponseDtos.add(friendsResponseDto);
        }

        return ResponseEntity.ok().body(friendsResponseDtos);
    }

    /**
     * 친구추가
     */
    public ResponseEntity<?> createFriends( FriendsRequestDto friendsRequestDto, User user) {

        Friends friends = new Friends(friendsRequestDto.getAddFriend_id(), user);
        // 댓글 저장
        friendsRepository.save(friends);

        return ResponseEntity.ok(new ResponseDto(true, "친구를 추가하였습니다."));
    }

    /**
     * 댓글 삭제
     */
    public ResponseEntity<?> deleteFriends(Long friendsId, User user) {

        Friends friends = friendsRepository.findById(friendsId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 친구입니다."));

        if (!friends.getUser().getId().equals(user.getId()))
            throw new IllegalArgumentException("본인만 삭제할 수 있습니다.");

        friendsRepository.delete(friends);
        return ResponseEntity.ok(new ResponseDto(true, "친구를 삭제되었습니다."));
    }
}
