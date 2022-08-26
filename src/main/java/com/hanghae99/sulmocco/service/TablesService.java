package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.ReplyResponseDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.dto.TablesRequestDto;
import com.hanghae99.sulmocco.dto.TablesResponseDto;
import com.hanghae99.sulmocco.model.*;
import com.hanghae99.sulmocco.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TablesService {

    private final TablesRepository tablesRepository;
    private final TableImageRepository tableImageRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final S3Service s3Service;

    /**
     * 술상 추천 작성 - ok
     */
    @Transactional
    public ResponseEntity<?> createTables(TablesRequestDto tablesRequestDto, User user) {

        String alcoholtag = tablesRequestDto.getAlcoholtag();
        // 술 태그 validation - 잘못된 주종 선택 시 Error 발생
        validateAlcoholtag(alcoholtag);
        // 술상 생성
        Tables tables = new Tables(tablesRequestDto, user);
        // 술상 저장
        tablesRepository.save(tables);

        if (tablesRequestDto.getImgUrlList().size() > 1) {
            // 이미지 등록 ( 0번째는 썸네일, 1부터 저장 )
            for (int i = 1; i < tablesRequestDto.getImgUrlList().size(); i++) {
                String tableImgUrl = tablesRequestDto.getImgUrlList().get(i);
                TableImage tableImage = new TableImage(tableImgUrl, tables);
                tableImageRepository.save(tableImage);
//            tables.addTableImage(tableImage);
            }
        }
        return ResponseEntity.ok().body(new ResponseDto(true, "술상이 올라갔습니다."));
    }


    /**
     * 술상 추천 수정 - ok
     */
    @Transactional
    public ResponseEntity<?> updateTables(Long tableId, TablesRequestDto tablesRequestDto, User user) {

        Tables findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));

        if (!findTable.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }

        String alcoholtag = tablesRequestDto.getAlcoholtag();
        // 술 태그 validation
        validateAlcoholtag(alcoholtag);

        // 술상 수정(업데이트)
        findTable.update(tablesRequestDto);

        // 이미지 수정(업데이트)
        tableImageRepository.deleteByTables(findTable);

        if (tablesRequestDto.getImgUrlList().size() > 1) {
            // 이미지 등록 ( 0번째는 썸네일, 1부터 저장 )
            for (int i = 1; i < tablesRequestDto.getImgUrlList().size(); i++) {
                String tableImgUrl = tablesRequestDto.getImgUrlList().get(i);
                TableImage tableImage = new TableImage(tableImgUrl, findTable);
                tableImageRepository.save(tableImage);
            }
        }

        return ResponseEntity.ok().body(new ResponseDto(true, "정상적으로 수정되었습니다."));
    }

    /**
     * 술상 추천 상세 - ok
     */
    @Transactional
    public ResponseEntity<?> getTableDetail(Long tableId, User user) {
        // 술상 조회
        Tables findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));

        // 북마크, 좋아요 조회
        Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndTables(user, findTable);
        Optional<Likes> likes = likeRepository.findByUserAndTables(user, findTable);
        // 조회한 유저의 ID 저장 (조회수)
        findTable.addViewUser(user.getUserId());

        // 술상 댓글 DB 조회
        List<Reply> replies = replyRepository.findAllByTablesOrderByCreatedAtDesc(findTable);

        List<ReplyResponseDto> replyResponseDtos = new ArrayList<>();
        for (Reply myReplies : replies) {
            ReplyResponseDto reviewResponseDto = new ReplyResponseDto(myReplies);
            replyResponseDtos.add(reviewResponseDto);
        }

        TablesResponseDto detailResponseDto = new TablesResponseDto(findTable,
                replyResponseDtos,
                checkIsBookmark(bookmark),
                checkIsLike(likes));

        return ResponseEntity.ok().body(detailResponseDto);
    }

    /**
     * 술상 추천 삭제 - ok
     */
    @Transactional
    public ResponseEntity<?> deleteTables(Long tableId, User user) {

        Tables findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));

        if (!findTable.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }
        // 이미지 삭제
        List<TableImage> imgUrls = findTable.getImgUrls();
        s3Service.deleteImages(imgUrls.stream().map(TableImage::getTableImgUrl)
                .collect(Collectors.toList()));
        // 술상 삭제
        tablesRepository.deleteById(tableId);

        return ResponseEntity.ok().body(new ResponseDto(true, "술상을 엎었습니다."));
    }

    /**
     * 술상 추천 목록
     */
    public ResponseEntity<?> getPagingTables(int page, int size, String sortBy, boolean isAsc, String alcohol) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Slice<Tables> tablesSlice = null;

        if (alcohol == null) {
            tablesSlice = tablesRepository.findAllTables(pageable); // 전체 목록
        } else {
            String[] splitAlcoholTag = alcohol.split(",");  // 소주,맥주,와인
            tablesSlice = tablesRepository.getTablesOrderByAlcoholTag(pageable, splitAlcoholTag);
        }

        Slice<TablesResponseDto> tablesResponseDtoSlice = TablesResponseDto.myPageTablesResponseDto(tablesSlice);

        return ResponseEntity.ok().body(tablesResponseDtoSlice);

    }

    /**
     * 본인이 작성한 술상 - ok
     */
    public ResponseEntity<?> getMyTables(int page, int size, User user) {

//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction);
//        Pageable pageable = PageRequest.of(page, size, sort);
        Pageable pageable = PageRequest.of(page, size);

        Slice<Tables> tablesSlice = tablesRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);

        Slice<TablesResponseDto> tablesResponseDtoSlice = TablesResponseDto.myPageTablesResponseDto(tablesSlice);
        return ResponseEntity.ok().body(tablesResponseDtoSlice);
    }

    /**
     * 술상 추천 검색
     */
    public ResponseEntity<?> getPagingTablesBySearch(int page, int size, String sortBy, boolean isAsc, String keyword) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Slice<Tables> tablesSlice = tablesRepository.getTablesBySearch(pageable, keyword);

        Slice<TablesResponseDto> tablesResponseDtoSlice = TablesResponseDto.myPageTablesResponseDto(tablesSlice);

        return ResponseEntity.ok().body(tablesResponseDtoSlice);
    }

    /**
     * 오늘의 술상 추천 - ok
     */
    public ResponseEntity<?> getTablesOrderByLikeCount() {

        Pageable pageable = PageRequest.ofSize(3);
        List<Tables> todayTables = tablesRepository.findByOrderByLikesSize(pageable);

        List<TablesResponseDto> todayTablesDtos = new ArrayList<>();
        for (Tables todayTable : todayTables) {
            todayTablesDtos.add(TablesResponseDto.todayTableDto(todayTable));
        }
        return ResponseEntity.ok().body(todayTablesDtos);
    }

    private void validateAlcoholtag(String alcoholtag) {
        if (!(alcoholtag.equals("맥주") || alcoholtag.equals("소주") || alcoholtag.equals("막걸리")
                || alcoholtag.equals("양주") || alcoholtag.equals("와인") || alcoholtag.equals("전통주")
                || alcoholtag.equals("기타"))) {
            throw new IllegalStateException("잘못된 술태그 입니다.");
        }
    }

    private boolean checkIsBookmark(Optional<Bookmark> bookmark) {
        return (!bookmark.isPresent()) ? false : true;
    }

    private boolean checkIsLike(Optional<Likes> likes) {
        return (!likes.isPresent()) ? false : true;
    }
}
