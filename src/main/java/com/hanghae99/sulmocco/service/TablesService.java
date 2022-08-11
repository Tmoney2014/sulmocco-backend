package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.dto.TablesRequestDto;
import com.hanghae99.sulmocco.dto.TablesResponseDto;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.TableImageRepository;
import com.hanghae99.sulmocco.repository.TablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TablesService {

    private final TablesRepository tablesRepository;
    private final TableImageRepository tableImageRepository;

    /**
     * 술상 추천 목록 - ok
     */
    public ResponseEntity<?> getTables() {
        List<Tables> tablesList = tablesRepository.findAll();
        List<TablesResponseDto> tablesResponseDtos = new ArrayList<>();
        tablesResponseDtos = tablesList.stream()
                                        .map(TablesResponseDto::new)
                                        .collect(Collectors.toList());
        return ResponseEntity.ok().body(tablesResponseDtos);
    }

    /**
     * 술상 추천 작성 - ok
     */
    @Transactional
    public ResponseEntity<?> createTables(TablesRequestDto tablesRequestDto, User user) {
        // 술상 생성
        Tables tables = new Tables(tablesRequestDto, user);
        // 술상 저장
        tablesRepository.save(tables);
        // 이미지 등록
        tableImageRepository.saveAll(tables.getImgUrls());
//        return new ResponseEntity<>(new ResponseDto(true, "술상 생성 완료"), HttpStatus.OK);
        return ResponseEntity.ok().body(new ResponseDto(true, "술상 생성 완료"));
    }

    /**
     * 술상 추천 수정
     */
    @Transactional
    public ResponseEntity<?> updateTables(Long tableId, TablesRequestDto tablesRequestDto, User user) {

        Tables findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));

        // 수정 전 이미지 삭제
        tableImageRepository.deleteByTables(findTable);
        if (!findTable.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }
        // 수정(업데이트)
        findTable.update(tablesRequestDto);
        // 이미지 저장
        tableImageRepository.saveAll(findTable.getImgUrls());

        // todo : 댓글, 북마크, 좋아요 추가 수정필요
        return ResponseEntity.ok().body(new TablesResponseDto(findTable));
    }

    /**
     * 술상 추천 상세
     */
    public ResponseEntity<?> getTableDetail(Long tableId) {

        Tables findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));

        // todo : 댓글, 북마크, 좋아요 추가 수정필요
        return ResponseEntity.ok().body(new TablesResponseDto(findTable));
    }

    /**
     * 술상 추천 삭제
     */
    @Transactional
    public ResponseEntity<?> deleteTables(Long tableId, User user) {

        Tables tables = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));

        if (!tables.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }
        // 술상 삭제 -> cascade 속성으로 연관관계 자동 삭제
        tablesRepository.deleteById(tableId);
        // 이미지 삭제
//        tableImageRepository.deleteByTables(tables);
        return ResponseEntity.ok().body(new ResponseDto(true, "정상 삭제되었습니다"));
    }
}
