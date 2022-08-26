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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final TablesRepository postRepository;
    private final UserRepository userRepository;


    //북마크 USERID와 TABLESID를 받아서 TABLES와 USER 연결하여 저장하기
    public ResponseEntity<?> postbookmark(Long userId, Long tableId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다"));
        Tables tables = postRepository.findById(tableId).orElseThrow(() -> new IllegalArgumentException("포스트가 없습니다"));
        Optional<Bookmark> bookmarkFound = bookmarkRepository.findByUserAndTables(user,tables);
        // 이미 북마크가 되어있는 POST 이면 이미 북마크한 게시글이라고 오류 메시지 날리기
        if(bookmarkFound.isPresent()){
            return new ResponseEntity<>("이미 북마크한 게시글입니다", HttpStatus.valueOf(400));
        }
        //새로운 BOOKMARK 객체 생성후 찾은 USER와 POST 넣어서 저장
        Bookmark bookmark = new Bookmark(user, tables);
        bookmarkRepository.save(bookmark);

        return new ResponseEntity<>("북마크를 등록했습니다.", HttpStatus.valueOf(200));
    }

    //북마크 삭제하기 JWT의 유저정보와 앞단에서 받은 TABLESID로 삭제하기
    public ResponseEntity<?> deletebookmark(UserDetailsImpl userDetails, Long tableId) {
        //유저가 갖고 있는 BOOKMARK를 찾아 LIST로 만들기
        //for문으로 앞단에서 갖고온 TABLESID 와 비교, 앞단에서 받아온 TABLESID와 일치하는 항목 삭제
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(userDetails.getUser());
        for (Bookmark bookmark : bookmarks) {
            if (bookmark.getTables().getId().equals(tableId)) {
                bookmarkRepository.deleteById(bookmark.getId());
            }
        }
        return new ResponseEntity<>("북마크가 삭제되었습니다.", HttpStatus.valueOf(200));
    }
    //마이페이지 에서 내가 북마크한 게시글 보기
    public ResponseEntity<?> getMyBookmark(int page, int size, User user) {

        Pageable pageable = PageRequest.of(page, size);

        Slice<Bookmark> bookmarkSlice = bookmarkRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);

        Slice<BookmarkResponseDto> bookmarkResponseDtoSlice = BookmarkResponseDto.myPageBookmarkResponseDto(bookmarkSlice);
        return ResponseEntity.ok().body(bookmarkResponseDtoSlice);
    }
}
