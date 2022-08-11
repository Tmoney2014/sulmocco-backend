package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.TablesRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.TablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TablesController {

    private final TablesService tablesService;

    // 술상 추천 목록
    @GetMapping("/api/tables")
    public ResponseEntity<?> getTables() {
        return tablesService.getTables();
    }

    // 술상 추천 작성
    @PostMapping("/api/tables")
    public ResponseEntity<?> createTables(@RequestBody @Valid TablesRequestDto tablesRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return tablesService.createTables(tablesRequestDto, user);
        }
        return ResponseEntity.badRequest().build();
    }

    // 술상 추천 수정
    @PutMapping("/api/tables/{tableId}")
    public ResponseEntity<?> updateTables(@PathVariable Long tableId,
                                          @RequestBody @Valid TablesRequestDto tablesRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return tablesService.updateTables(tableId, tablesRequestDto, user);
        }
        return ResponseEntity.badRequest().build();
    }

    // 술상 추천 상세
    @GetMapping("/api/tables/{tableId}")
    public ResponseEntity<?> tableDetail(@PathVariable Long tableId) {
        return tablesService.getTableDetail(tableId);
    }

    // 술상 추천 삭제
    @DeleteMapping("/api/tables/{tableId}")
    public ResponseEntity<?> updateTables(@PathVariable Long tableId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            return tablesService.deleteTables(tableId, user);
        }
        return ResponseEntity.badRequest().build();
    }
}
