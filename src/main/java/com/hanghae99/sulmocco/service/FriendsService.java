package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.FriendsResponseDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.model.Friends;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.FriendsRepository;
import com.hanghae99.sulmocco.repository.RoomRepository;
import com.hanghae99.sulmocco.repository.UserRepository;
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

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getFriends(User user) {

        List<Long> friendsIdList = friendsRepository.findByUser(user);
        List<FriendsResponseDto> friendsResponseDtos = new ArrayList<>();

        for (Long friendsId : friendsIdList) {
            User findFriendsUser = userRepository.findByUserId(friendsId);
            //친구 추가한 사용자가 Id로 생성된 방이 있는지 없는지 확인
            boolean isonair = roomRepository.findById(friendsId).isPresent();

            FriendsResponseDto friendsResponseDto = new FriendsResponseDto(findFriendsUser, isonair);
            friendsResponseDtos.add(friendsResponseDto);
        }
        return ResponseEntity.ok().body(friendsResponseDtos);
    }

    /**
     * 친구추가
     */
    public ResponseEntity<?> createFriends(String username, User user) {

        User addfriends = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Friends friends = new Friends(addfriends.getUserId(), user);

        friendsRepository.save(friends);

        return ResponseEntity.ok().body(new ResponseDto(true, "친구추가 완료"));

    }


    public ResponseEntity<?> deleteFriends(String username, User user) {

        User deleteFriends = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 친구입니다."));
        Long userId = deleteFriends.getUserId();
//        if (!friends.getUser().getId().equals(user.getId()))
//            throw new IllegalArgumentException("본인만 삭제할 수 있습니다.");

        Friends deleteFriend = friendsRepository.findByAddFriendIdAndUser(userId, user);
        friendsRepository.delete(deleteFriend);

        return ResponseEntity.ok(new ResponseDto(true, "친구를 삭제되었습니다."));
    }
}
