package com.hanghae99.sulmocco.mainpagedata;

import com.hanghae99.sulmocco.model.Banner;
import com.hanghae99.sulmocco.model.Product;
import com.hanghae99.sulmocco.model.Tables;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.BannerRepository;
import com.hanghae99.sulmocco.repository.ProductRepository;
import com.hanghae99.sulmocco.repository.TablesRepository;
import com.hanghae99.sulmocco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class MainPageDataRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TablesRepository tablesRepository;
    private final BannerRepository bannerRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User testUser1 = new User("testUser", bCryptPasswordEncoder.encode("테ster!23"), "테스트 백", "술찌", "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png");
        userRepository.save(testUser1);
        createData(27, testUser1);

        Banner banner1 = new Banner(1L, "https://s3.ap-northeast-2.amazonaws.com/bucketservice/banner01.png", "https://www.ob.co.kr/stellaartois");
        Banner banner2 = new Banner(2L, "https://s3.ap-northeast-2.amazonaws.com/bucketservice/banner02.png", "https://www.ob.co.kr/hoegaarden");
        Banner banner3 = new Banner(3L, "https://s3.ap-northeast-2.amazonaws.com/bucketservice/banner03.png", "https://www.ob.co.kr/budweiser");
        bannerRepository.save(banner1);
        bannerRepository.save(banner2);
        bannerRepository.save(banner3);

        Product product1 = new Product(1L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", 12900, "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "와인");
        Product product2 = new Product(2L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", 12900, "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "소주");
        Product product3 = new Product(3L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", 12900, "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "맥주");
        Product product4 = new Product(4L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", 12900, "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "막걸리");
        Product product5 = new Product(5L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", 12900, "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "맥주");
        Product product6 = new Product(6L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", 12900, "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "전통주");
        Product product7 = new Product(7L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", 12900, "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "와인");
        Product product8 = new Product(8L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", 12900, "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "와인");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);

    }

    private void createData(int count, User testUser1) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;

        for (int i = 0; i < count; i++) {
            Random random = new Random();

            String title = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            String content = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            int subjectNumber = random.nextInt(6) + 1;
            String alcoholTag;

            switch (subjectNumber) {
                case 1:
                    alcoholTag = "소주";
                    break;
                case 2:
                    alcoholTag = "맥주";
                    break;
                case 3:
                    alcoholTag = "와인";
                    break;
                case 4:
                    alcoholTag = "막걸리";
                    break;
                case 5:
                    alcoholTag = "양주";
                    break;
                case 6:
                    alcoholTag = "전통주";
                    break;
                case 7:
                    alcoholTag = "기타";
                    break;
                default:
                    alcoholTag = null;
                    break;

            }

            User user = testUser1;

            String freetag = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

//            String thumbnailImgUrl = "https://bucketservice.s3.ap-northeast-2.amazonaws.com/homeTmate/roomdefault.png";
            String thumbnailImgUrl = "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png";

            Tables tables = new Tables(title, content, alcoholTag, freetag, thumbnailImgUrl, user);

            tablesRepository.save(tables);

        }
    }
}
