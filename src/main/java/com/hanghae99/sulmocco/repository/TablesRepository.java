package com.hanghae99.sulmocco.repository;

import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TablesRepository extends JpaRepository<Tables, Long> {

    //    @Query("select t from Tables t ORDER BY t.user.id DESC, t.id DESC ")
    Slice<Tables> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    @Query("select t from Tables t ")
    Slice<Tables> findAllTables(Pageable pageable);

    // 오늘의 술상 추천 - 좋아요 3순위
    @Query("select t from Tables t ORDER BY t.likes.size DESC, t.id DESC ")
    List<Tables> findByOrderByLikesSize(Pageable pageable);

    // 검색 - 제목, 내용, 추천술태그, 자유태그에 검색어를 포함한 리스트
//    @Query("select t from Tables t " +
//            "where t.title LIKE %:title% Or t.content LIKE %:content% " +
//            "Or t.alcoholTag LIKE %:alcoholTag% Or t.freeTag LIKE %:freeTag% ")
//    Slice<Tables> getTablesBySearch(Pageable pageable, @Param("title") String title,
//                                    @Param("content")String content,
//                                    @Param("alcoholTag") String alcoholTag,
//                                    @Param("freeTag") String freeTag);

    @Query("select t from Tables t where t.title LIKE %:keyword% Or t.content LIKE %:keyword% Or t.alcoholTag LIKE %:keyword% Or t.freeTag LIKE %:keyword% ")
    Slice<Tables> getTablesBySearchV3(Pageable pageable, @Param("keyword") String keyword);

    //    @Query("select t from Tables t where t.alcoholTag in :splitAlcoholTag order by t.id DESC ")
    @Query("select t from Tables t where t.alcoholTag in :splitAlcoholTag ")
    Slice<Tables> getTablesOrderByAlcoholTag(Pageable pageable, String[] splitAlcoholTag);

    // 인기순
    //    @Query("select t from Tables t where t.alcoholTag in :splitAlcoholTag order by t.viewUserList.size DESC ")
}
