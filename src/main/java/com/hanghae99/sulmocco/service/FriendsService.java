package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.FriendsResponseDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.model.Friends;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.FriendsRepository;
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

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getFriends(User user) {

        // 친구 조회
//        Friends findFriends = friendsRepository.findByUser(user).orElseThrow(
//                () -> new IllegalArgumentException("친구가 존재하지 않습니다."));

        List<Long> friendsIdList = friendsRepository.findByUser(user);
        List<FriendsResponseDto> friendsResponseDtos = new ArrayList<>();

        for (Long friendsId : friendsIdList) {
            User findFriendsUser = userRepository.findByUserId(friendsId);
            FriendsResponseDto friendsResponseDto = new FriendsResponseDto(findFriendsUser);
            friendsResponseDtos.add(friendsResponseDto);
        }

        return ResponseEntity.ok().body(friendsResponseDtos);
    }


//    public ResponseEntity<?> getbookmark(UserDetailsImpl userDetails) {
//        //USER 로 찾아 BOOKMARK LIST 작성
//        List<Bookmark> bookmarks = bookmarkRepository.findByUser(userDetails.getUser());
//        //앞단으로 리스폰스 해줄 DTO 형식을 새로 만들어서
//        List<BookmarkResponseDto> bookmarkResponseDtoList = new ArrayList<>();
//        //FOR문을 돌려 필요한 내용을 DTO 에 하나씩 담아 ADD 후 DTOLIST 를 앞단으로 리턴
//        for(Bookmark bookmark : bookmarks) {
//            bookmarkResponseDtoList.add(new BookmarkResponseDto(bookmark.getTables().getTitle(),bookmark.getTables().getUser().getUsername(),bookmark.getTables().getContent()
//                    ,bookmark.getTables().getId(),bookmark.getTables().getLikes().size(),bookmark.getTables().getViewUserList().size(),bookmark.getTables().getAlcoholTag(),bookmark.getTables().getFreeTag(),bookmark.getUser().getProfileUrl()));
//
//        }

    /**
     * 친구추가
     */
    public ResponseEntity<?> createFriends( String username, User user) {

        User addfriends = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Friends friends = new Friends(addfriends.getUserId(),user);

        friendsRepository.save(friends);

        return ResponseEntity.ok().body(new ResponseDto(true, "친구추가 완료"));

//
//        Friends friends = new Friends(friendsRequestDto.getAddFriend_id(), user);
        // 댓글 저장
//        friendsRepository.save(friends);


    }


    public ResponseEntity<?> deleteFriends(String username, User user) {

       User friends = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 친구입니다."));
        Long userId = friends.getUserId();
//        if (!friends.getUser().getId().equals(user.getId()))
//            throw new IllegalArgumentException("본인만 삭제할 수 있습니다.");

        Friends deleteFriend = friendsRepository.findByUserIdAndUser(userId,user);
        friendsRepository.delete(deleteFriend);

        return ResponseEntity.ok(new ResponseDto(true, "친구를 삭제되었습니다."));
    }
}
