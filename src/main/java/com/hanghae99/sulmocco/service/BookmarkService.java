package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.BookmarkResponseDto;
import com.hanghae99.sulmocco.model.Bookmark;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.BookmarkRepository;
import com.hanghae99.sulmocco.repository.TablesRepository;
import com.hanghae99.sulmocco.repository.UserRepository;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final TablesRepository postRepository;
    private final UserRepository userRepository;


    //북마크 USERID와 POSTID를 받아서 POST와 USER 연결하여 저장하기
    public ResponseEntity<?> postbookmark(Long userId, Long tableId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다"));
        Tables post = postRepository.findById(tableId).orElseThrow(() -> new IllegalArgumentException("포스트가 없습니다"));
        Optional<Bookmark> bookmarkFound = bookmarkRepository.findByUserAndTables(user,tableId);
        // 이미 북마크가 되어있는 POST 이면 이미 북마크한 게시글이라고 오류 메시지 날리기
        if(bookmarkFound.isPresent()){
            return new ResponseEntity<>("북마크를 이미 한 게시글입니다", HttpStatus.valueOf(400));
        }
        //새로운 BOOKMARK 객체 생성후 찾은 USER와 POST 넣어서 저장
        Bookmark bookmark = new Bookmark(user, post);
        bookmarkRepository.save(bookmark);

        return new ResponseEntity<>("북마크를 등록했습니다.", HttpStatus.valueOf(200));
    }

    //북마크 삭제하기 JWT의 유저정보와 앞단에서 받은 POSTID로 삭제하기
    public ResponseEntity<?> deletebookmark(UserDetailsImpl userDetails, Long tableId) {
        //유저가 갖고 있는 BOOKMARK를 찾아 LIST로 만들기
        //for문으로 앞단에서 갖고온 POSTID 와 비교, 앞단에서 받아온 POSTID와 일치하는 항목 삭제
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(userDetails.getUser());
        for (Bookmark bookmark : bookmarks) {
            if (bookmark.getTables().getId().equals(tableId)) {
                bookmarkRepository.deleteById(bookmark.getId());
            }
        }
        return new ResponseEntity<>("북마크가 삭제되었습니다.", HttpStatus.valueOf(200));
    }

    //JWT 의 유저 정보로 모든 북마크 보기
    public ResponseEntity<?> getbookmark(UserDetailsImpl userDetails) {
        //USER 로 찾아 BOOKMARK LIST 작성
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(userDetails.getUser());
        //앞단으로 리스폰스 해줄 DTO 형식을 새로 만들어서
        List<BookmarkResponseDto> bookmarkResponseDtoList = new ArrayList<>();
        //FOR문을 돌려 필요한 내용을 DTO 에 하나씩 담아 ADD 후 DTOLIST 를 앞단으로 리턴
        for(Bookmark bookmark : bookmarks) {
            bookmarkResponseDtoList.add(new BookmarkResponseDto(bookmark.getTables().getTitle(),bookmark.getTables().getUser().getUsername(),bookmark.getTables().getContent()
                    ,bookmark.getTables().getId(),bookmark.getTables().getLikes().size(),bookmark.getTables().getViewount(),bookmark.getTables().getAlcoholTag(),bookmark.getTables().getFreeTag(),bookmark.getUser().getProfileUrl()));

        }

//        private String title;
//        private String username;
//        private String content;
//        private Long tablesId;
//        private Long likecount;  //@OneToMany(mappedBy = "tables", cascade = CascadeType.ALL //private List<Like> likes; 사이즈로 갖고옴.
//        private Long viewcount;  //콜렉션으로 유저ID 저장 // 사이즈로 갖고옴.
//        private String alcoholtag;
//        private String freetag;
//        private String profileimgurl; // 작성자 profileimg


        return new ResponseEntity<>(bookmarkResponseDtoList, HttpStatus.valueOf(200));

    }
}
