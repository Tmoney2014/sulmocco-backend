package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.TablesRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.TablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TablesController {

    private final TablesService tablesService;

    // 본인이 작성한 술상 목록
    @GetMapping("/api/mypage/tables")
    public ResponseEntity<?> getMyTables(@RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        page = page - 1;
        User user = userDetails.getUser();
        return tablesService.getMyTables(page, size, user);
    }

    // 술상 추천 목록
    @GetMapping("/api/tables")
    public ResponseEntity<?> getPagingTables(@RequestParam("page") int page,        // 1부터 시작
                                             @RequestParam("size") int size,        // 9개
                                             @RequestParam("sortBy") String sortBy, // 정렬 항목
                                             @RequestParam("isAsc") boolean isAsc,
                                             @RequestParam(value = "alcohol", required = false) String alcohol) {
        page = page - 1;
        return tablesService.getPagingTables(page, size, sortBy, isAsc, alcohol);
    }

    // 술상 추천 검색
    @GetMapping("/api/tables/search")
    public ResponseEntity<?> getPagingTablesBySearchV1(@RequestParam("page") int page,        // 1부터 시작
                                                       @RequestParam("size") int size,        // 9개
                                                       @RequestParam("sortBy") String sortBy, // 정렬 항목
                                                       @RequestParam("isAsc") boolean isAsc,
                                                       @RequestParam("keyword") String keyword) {
        page = page - 1;
        return tablesService.getPagingTablesBySearch(page, size, sortBy, isAsc, keyword);
    }

    // 오늘의 술상 추천 - 추천수 Top 3
    @GetMapping("/api/tables/main")
    public ResponseEntity<?> getTablesOrderByViewCount() {
        return tablesService.getTablesOrderByLikeCount();
    }

    // 술상 추천 작성
    @PostMapping("/api/tables")
    public ResponseEntity<?> createTables(@RequestBody @Valid TablesRequestDto tablesRequestDto,
                                          BindingResult result,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return tablesService.createTables(tablesRequestDto, user);
    }

    // 술상 추천 수정
    @PutMapping("/api/tables/{tableId}")
    public ResponseEntity<?> updateTables(@PathVariable Long tableId,
                                          @RequestBody @Valid TablesRequestDto tablesRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return tablesService.updateTables(tableId, tablesRequestDto, user);
    }

    // 술상 추천 상세
    @GetMapping("/api/tables/{tableId}")
    public ResponseEntity<?> tableDetail(@PathVariable Long tableId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return tablesService.getTableDetail(tableId, user);
    }

    // 술상 추천 삭제
    @DeleteMapping("/api/tables/{tableId}")
    public ResponseEntity<?> updateTables(@PathVariable Long tableId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return tablesService.deleteTables(tableId, user);
    }
}
