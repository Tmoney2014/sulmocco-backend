package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.tables.TablesRequestDto;
import com.hanghae99.sulmocco.model.Dish;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.*;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
class DishServiceTest {

    @Mock
    TablesRepository tablesRepository;
    @Mock
    UserRepository userRepository;
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
    @Mock
    UserDetailsImpl userDetails;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private TablesService tablesService;


    @Test
    @DisplayName("술상 추천 작성")
    void createTables() {

        // given
        User user = new User("testA", "2222@@@@", "개발자", "술찌", "ㅁㄴㅇㄹ");

        Long userId = 1L;
        String title = "술상 제목";
        String username = user.getUsername();
        String content = "술상 테스트 제목";
        String thumbnail = "ㄷㅈ";
        List<String> imgUrlList = new ArrayList<>();
        String alcoholtag = "맥주";
        String freetag = "자유";

        TablesRequestDto tablesRequestDto = new TablesRequestDto(title, username, content, thumbnail, imgUrlList, alcoholtag, freetag);

        Dish dish = new Dish(tablesRequestDto, user);
        when(tablesRepository.save(any(Dish.class))).thenReturn(dish);

        // when
        ResponseEntity<?> result = tablesService.createTables(tablesRequestDto, user);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("술상 추천 수정 - 술태그 수정")
    void updateTables_Normal() {

        // given
        User user = new User("testA", "2222@@@@", "개발자", "술찌", "ㅁㄴㅇㄹ");

        String title = "술상 제목";
        String username = user.getUsername();
        String content = "술상 테스트 제목";
        String thumbnail = "ㄷㅈ";
        List<String> imgUrlList = new ArrayList<>();
        String alcoholtag = "소주";
        String freetag = "자유";

        TablesRequestDto tablesRequestDto = new TablesRequestDto(title, username, content, thumbnail, imgUrlList, alcoholtag, freetag);
        Long tablesId = 1L;


        Dish dish = new Dish(tablesRequestDto, user);

        when(tablesRepository.findById(tablesId))
                .thenReturn(Optional.of(dish));

        // when
        ResponseEntity<?> result = tablesService.updateTables(tablesId, tablesRequestDto, user);

        // then
//        assertEquals(myprice, result.getMyprice());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

//    @Test
//    @DisplayName("술상추천 수정 - 잘못된 술태그로 수정")
//    void updateProduct_Failed() {
//        // given
//        Long productId = 100L;
//        int myprice = MIN_MY_PRICE - 50;
//
//        ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto(
//                myprice
//        );
//
//        ProductService productService = new ProductService(productRepository);
//
//        // when
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            productService.updateProduct(productId, requestMyPriceDto);
//        });
//
//        // then
//        assertEquals(
//                "유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.",
//                exception.getMessage()
//        );
//    }
}