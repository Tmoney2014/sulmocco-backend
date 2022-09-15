package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.Likes;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.LikeRepository;
import com.hanghae99.sulmocco.repository.TablesRepository;
import com.hanghae99.sulmocco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final LikeRepository likesRepository;
    private final TablesRepository tablesRepository;
    private final UserRepository userRepository;

    //좋아요 저장
    @Transactional
    public ResponseEntity<?> postLikes(Long userId, Long tableId) {
        //USERID 와 TABLESID 아이디로 USER 와 TABLES 를 찾아서 저장
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다"));
        Tables tables = tablesRepository.findById(tableId).orElseThrow(() -> new IllegalArgumentException("술상게시물이 없습니다"));

        Optional<Likes> Likefound = likesRepository.findByUserAndTables(user, tables);
        //이미 있는 USER와 TABLES 가 들어오면 오류 메시지 전달
        if (Likefound.isPresent()) {
            return new ResponseEntity<>("좋아요를 이미 하신 게시글입니다", HttpStatus.valueOf(400));
        }
        //새로운 Likes 생성후 USER 와 POST 넣고 저장
        Likes like = new Likes(user, tables);
        likesRepository.save(like);

        return new ResponseEntity<>("좋아요 등록 성공", HttpStatus.valueOf(200));

    }

    //좋아요 삭제
    @Transactional
    public ResponseEntity<?> deleteLikes(User user, Long tableId) {

        Tables findTable = tablesRepository.findById(tableId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 술상입니다"));

        // USERID 로 좋아요 한 개시물들을 리스트에 담아서
        List<Likes> likes = likesRepository.findByUser(user);

        //for문으로 앞단에서 받아온 TABLESID 와 같은 좋아요 삭제
        for (Likes like : likes) {
            if (like.getTables().getId().equals(findTable.getId())) {
                likesRepository.deleteById(like.getId());
            }
        }
        return new ResponseEntity<>("좋아요 삭제 성공", HttpStatus.valueOf(200));
    }
}