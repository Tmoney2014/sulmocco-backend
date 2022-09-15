package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.tables.TablesRequestDto;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TablesServiceTest {

    @Mock
    TablesRepository tablesRepository;
    @Mock
    TableImageRepository tableImageRepository;
    @Mock
    ReplyRepository replyRepository;
    @Mock
    LikeRepository likeRepository;
    @Mock
    BookmarkRepository bookmarkRepository;
    @Mock
    S3Service s3Service;
    @Mock
    User user;
    private TablesService tablesService;
    private String title;
    private String username;
    private String content;
    private String thumbnail;
    private List<String> imgUrlList;
    private String alcoholtag;
    private String freetag;

    @BeforeEach
    void setup() {
        title = "술상 제목";
        username = user.getUsername();
        content = "술상 테스트 제목";
        thumbnail = "defaultThumbnail.jpg";
        imgUrlList = new ArrayList<>();
        alcoholtag = "맥주";
        freetag = "자유";
        user = User.builder()
                .userId(1L)
                .id("Table01")
                .username("testUser")
                .password("1111!!!!")
                .level("술찌")
                .profileUrl("default.jpg")
                .build();
        tablesService = new TablesService(tablesRepository, tableImageRepository ,
                        replyRepository ,likeRepository ,bookmarkRepository, s3Service);
    }

    @Test
    @DisplayName("술상 추천 작성")
    @Order(1)
    void createTable() {

        // given
        TablesRequestDto tablesRequestDto = new TablesRequestDto(title, username, content, thumbnail, imgUrlList, alcoholtag, freetag);
        Tables tables = new Tables(tablesRequestDto, user);

        // when
        when(tablesRepository.save(any(Tables.class))).thenReturn(tables);
        ResponseEntity<?> result = tablesService.createTables(tablesRequestDto, user);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("술상 추천 수정 - 알맞은 술태그 수정 & 작성자 수정")
    @Order(2)
    void updateTable_Normal() {

        // given
        String alcoholtag = "소주";
        TablesRequestDto tablesRequestDto = new TablesRequestDto(title, username, content, thumbnail, imgUrlList, alcoholtag, freetag);
        Tables tables = new Tables(tablesRequestDto, user);

        // when
        when(tablesRepository.findById(1L)).thenReturn(Optional.of(tables));
        ResponseEntity<?> result = tablesService.updateTables(1L, tablesRequestDto, user);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("술상추천 수정 - 잘못된 술태그로 수정")
    @Order(3)
    void updateTable_FailedV1() {

        // given
        String alcoholtag = "소맥";
        TablesRequestDto tablesRequestDto = new TablesRequestDto(title, username, content, thumbnail, imgUrlList, alcoholtag, freetag);
        Tables tables = new Tables(tablesRequestDto, user);

        // when
        when(tablesRepository.findById(1L)).thenReturn(Optional.of(tables));
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            tablesService.updateTables(1L, tablesRequestDto, user);
        });

        // then
        assertEquals("잘못된 술태그 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("술상추천 수정 - 작성자가 아닌 경우")
    @Order(4)
    void updateTable_FailedV2() {

        // given
        User userB = new User("testB", "2222@@@@", "개발자B", "술찌", "default.jpg");
        String username = userB.getUsername();

        TablesRequestDto tablesRequestDto = new TablesRequestDto(title, username, content, thumbnail, imgUrlList, alcoholtag, freetag);
        Tables tables = new Tables(tablesRequestDto, user);

        // when
        when(tablesRepository.findById(1L)).thenReturn(Optional.of(tables));
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            tablesService.updateTables(1L, tablesRequestDto, userB);
        });

        // then
        assertEquals("작성자만 수정할 수 있습니다.", exception.getMessage());
    }
}