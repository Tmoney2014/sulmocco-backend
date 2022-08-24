package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

//북마크
    @PostMapping("/api/tables/{tableId}/bookmark")
    public ResponseEntity<?> postbookmark(@AuthenticationPrincipal UserDetailsImpl userDetails , @PathVariable Long tableId) {
        Long userId = userDetails.getUser().getUserId();
        return bookmarkService.postbookmark(userId,tableId);
    }

//북마크 삭제
    @DeleteMapping("/api/tables/{tableId}/bookmark")
    public ResponseEntity<?> deletetbookmark(@AuthenticationPrincipal UserDetailsImpl userDetails , @PathVariable Long tableId)  {
        return bookmarkService.deletebookmark(userDetails,tableId);
    }

    //마이페이지 -북마크한 술상
    @GetMapping("/api/mypage/bookmark")
    public ResponseEntity<?> getbookmark(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return bookmarkService.getbookmark(userDetails);
    }
    

}