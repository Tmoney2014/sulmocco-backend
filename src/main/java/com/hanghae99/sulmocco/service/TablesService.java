package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.ReplyResponseDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.dto.TablesRequestDto;
import com.hanghae99.sulmocco.dto.TablesResponseDto;
import com.hanghae99.sulmocco.model.*;
import com.hanghae99.sulmocco.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TablesService {

    private final TablesRepository tablesRepository;
    private final TableImageRepository tableImageRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;

    /**
     * 술상 추천 목록
     */
    public ResponseEntity<?> getTables() {
        // todo : 페이징(무한스크롤) 및 검색 기능 추가 필요

        List<Tables> tablesList = tablesRepository.findAll();
        HashMap<Tables, String> allTables = new HashMap<>();

//        for (Tables tables : tablesList) {
//            TableImage thumbnail = tableImageRepository.findByTablesAndIsThumbnailImage(tables, true);
//            allTables.put(thumbnail.getTables(), thumbnail.getTableImgUrl());
//            for (Tables tables1 : allTables.keySet()) {
//                tables1
//            }
//            allTables.keySet()
//        }

        List<TablesResponseDto> tablesResponseDtos = new ArrayList<>();
//        List<TablesResponseDto> tablesResponseDtos = tablesList.stream()
//                .map(TablesResponseDto::new)
//                .collect(Collectors.toList());

        return ResponseEntity.ok().body(tablesResponseDtos);
    }

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
        // 썸네일 등록
        Thumbnail thumbnail = new Thumbnail(tablesRequestDto.getThumbnail(), tables);
        thumbnailRepository.save(thumbnail);
        // 이미지 등록 ( 0번째는 썸네일, 1부터 저장 )
        for (int i = 1; i < tablesRequestDto.getImgUrlList().size(); i++) {
            String tableImgUrl = tablesRequestDto.getImgUrlList().get(i);
            TableImage tableImage = new TableImage(tableImgUrl, tables);
            tableImageRepository.save(tableImage);
//            tables.addTableImage(tableImage);
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

        // 이미지 수정(업데이트)
//        Thumbnail thumbnail = thumbnailRepository.findByTables(findTable);
        Thumbnail thumbnail = findTable.getThumbnail();
        thumbnail.updateImgUrl(tablesRequestDto.getThumbnail());
        tableImageRepository.deleteByTables(findTable);
        for (int i = 1; i < tablesRequestDto.getImgUrlList().size(); i++) {
            String tableImgUrl = tablesRequestDto.getImgUrlList().get(i);
            TableImage tableImage = new TableImage(tableImgUrl, findTable);
            tableImageRepository.save(tableImage);
        }
        // 술상 수정(업데이트)
        findTable.update(tablesRequestDto, thumbnail);

        return ResponseEntity.ok().body(new ResponseDto(true, "정상적으로 수정되었습니다."));
    }

    /**
     * 술상 추천 상세 - ok
     */
    public ResponseEntity<?> getTableDetail(Long tableId, User user) {
        // 술상 조회
        Tables findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다."));
        // 썸네일 조회
//        Thumbnail thumbnail = thumbnailRepository.findByTables(findTable);
//        Thumbnail thumbnail = findTable.getThumbnail();

        // 북마크, 좋아요 조회
        Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndTables(user, findTable.getId());
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
        // 술상 삭제 -> cascade 속성으로 연관관계 자동 삭제
        tablesRepository.deleteById(tableId);

        return ResponseEntity.ok().body(new ResponseDto(true, "술상을 엎었습니다."));
    }


//    public ResponseEntity<?> getPagingTables(int page, int size, String sortBy, boolean isAsc) {
//
////        List<Tables> tablesList = tablesRepository.findAll();
////        List<TablesResponseDto> tablesResponseDtos = tablesList.stream()
////                .map(TablesResponseDto::new)
////                .collect(Collectors.toList());
////        return ResponseEntity.ok().body(tablesResponseDtos);
//
//        // ----------------------페이징 처리-------------------------//
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//        // Pageable pageable2 = new PageRequest(page, size, sort); 우리에겐 이 형태가 익숙하지만
//        // static 함수(=of)를 사용해서 필요인자를 받아서 new를 해준다.
//        // --------------------------------------------------------//
//
//        List<TablesResponseDto> reviewResponseDtos = new ArrayList<>();
//
//        // 댓글 가져오기
//        Page<Reply> myReviews = reviewRepository.findOrderByCreatedAtDesc(user, pageable);
//        for (Review myReview : myReviews) {
//            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(myReview);
//            reviewResponseDtos.add(reviewResponseDto);
//        }
////      return  reviewResponseDtos = reviewRepository.findByUserOrderByCreatedAtDesc(user, pageable).stream()
////                .map(myReview -> new ReviewResponseDto(myReview)).collect(Collectors.toList());
//        return reviewResponseDtos;
//    }

    /**
     * 본인이 작성한 술상 - ok ( 2번응답으로 바뀔 가능성 있음 )
     */
    public ResponseEntity<?> getMyTables(int page, int size, boolean isAsc, User user) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<TablesResponseDto> tablesResponseDtos = new ArrayList<>();

        Slice<Tables> tablesSlice = tablesRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);
        // 1번
        for (Tables tables : tablesSlice) {
//            Thumbnail thumbnail = thumbnailRepository.findByTables(tables);
            TablesResponseDto tablesResponseDto = new TablesResponseDto(tables);
            tablesResponseDtos.add(tablesResponseDto);
        }
        // 1번 응답
        return ResponseEntity.ok().body(tablesResponseDtos);

        // 2번
//        Slice<TablesResponseDto> tablesResponseDtoSlice = TablesResponseDto.myPageTablesResponseDto(tablesSlice);
        // 2번 응답
//        return ResponseEntity.ok().body(tablesResponseDtoSlice);
    }

    /**
     * 오늘의 술상 추천 - ok
     */
    public ResponseEntity<?> getTablesOrderByLikeCount() {

        Pageable pageable = PageRequest.ofSize(3);
        List<Tables> todayTables = tablesRepository.findByOrderByLikesSize(pageable);

        List<TablesResponseDto> todayTablesDtos = new ArrayList<>();
        for (Tables todayTable : todayTables) {
//            Thumbnail thumbnail = thumbnailRepository.findByTables(todayTable);
            todayTablesDtos.add(TablesResponseDto.todayTableDto(todayTable));
        }
        return ResponseEntity.ok().body(todayTablesDtos);
    }

    private void validateAlcoholtag(String alcoholtag) {
        if (Stream.of("맥주", "소주", "양주", "막걸리", "와인", "전통주").anyMatch(s -> !alcoholtag.equals(s))) {
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
