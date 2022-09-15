//package com.hanghae99.sulmocco.mainpagedata;
//
//import com.hanghae99.sulmocco.model.Banner;
//import com.hanghae99.sulmocco.model.Product;
//import com.hanghae99.sulmocco.model.Tables;
//import com.hanghae99.sulmocco.model.User;
//import com.hanghae99.sulmocco.repository.BannerRepository;
//import com.hanghae99.sulmocco.repository.ProductRepository;
//import com.hanghae99.sulmocco.repository.TablesRepository;
//import com.hanghae99.sulmocco.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Random;
//
//@Component
//@RequiredArgsConstructor
//@Transactional
//public class MainPageDataRunner implements ApplicationRunner {
//
//    private final UserRepository userRepository;
//
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    private final TablesRepository tablesRepository;
//    private final BannerRepository bannerRepository;
//    private final ProductRepository productRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
////        테스트 유저 1
//        User testUser1 = new User("testUser", bCryptPasswordEncoder.encode("테ster!23"), "테스트 백", "술찌", "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png");
//        userRepository.save(testUser1);
////        테스트 유저 2.3.4.5
//        User testUser2 = new User("testUser2", bCryptPasswordEncoder.encode("2222@@@@"), "테스트2", "0", "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png");
//        User testUser3 = new User("testUser3", bCryptPasswordEncoder.encode("3333####"), "테스트3", "1", "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png");
//        User testUser4 = new User("testUser4", bCryptPasswordEncoder.encode("4444$$$$"), "테스트4", "2", "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png");
//        User testUser5 = new User("testUser5", bCryptPasswordEncoder.encode("5555%%%%"), "테스트5", "3", "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png");
//        User testUser6 = new User("testUser6", bCryptPasswordEncoder.encode("6666^^^^"), "테스트5", "3", "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png");
//        userRepository.save(testUser2);
//        userRepository.save(testUser3);
//        userRepository.save(testUser4);
//        userRepository.save(testUser5);
//
////        테스트 데이터 생성
//        createData(2, testUser1);
//        Banner banner1 = new Banner(1L, "https://s3.ap-northeast-2.amazonaws.com/bucketservice/sulmoggoBanner01.png", "https://www.instagram.com/sulmoggo_official/");
////        Banner banner2 = new Banner(2L, "https://s3.ap-northeast-2.amazonaws.com/bucketservice/banner02.jpg", "https://www.ob.co.kr/hoegaarden");
////        Banner banner3 = new Banner(3L, "https://s3.ap-northeast-2.amazonaws.com/bucketservice/banner03.jpg", "https://www.ob.co.kr/budweiser");
//        bannerRepository.save(banner1);
////        bannerRepository.save(banner2);
////        bannerRepository.save(banner3);
//
//        Product product1 = new Product(1L, "https://img.bbq.co.kr:449/uploads/bbq_d/thumbnail/BBQ_앱_썸네일(480x480)_후라이드류_황금올리브치킨_수정_out.png", "황금올리브치킨", "20,000", "https://www.bbq.co.kr/menu/menuView.asp?midx=2247&cidx=114&cname=%ED%99%A9%EC%98%AC+%EC%8B%9C%EA%B7%B8%EB%8B%88%EC%B2%98", "맥주");
//        Product product2 = new Product(2L, "https://image.gsecretail.com/md/data/image/resize/20/02/8809355310220/8809355310220_M_500.jpg", "하남쭈꾸미(보통매운맛) 420g", "11,800", "https://www.gsfresh.com/md/product_detail?itemId=8809355310220&storId=3039&supplFirmCd=70041", "소주");
//        Product product3 = new Product(3L, "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/pd/22/0/1/6/3/1/7/QMtDz/4259016317_B.jpg", "밥도둑 하동녹차 명란김", "29,900", "https://www.11st.co.kr/products/4259016317", "맥주");
//        Product product4 = new Product(4L, "https://shop-phinf.pstatic.net/20220504_122/1651643159840Kipvd_JPEG/52779055307690792_154124037.jpg?type=m510", "국민어포 빠사삭 80g x 10봉", "34,500", "https://smartstore.naver.com/cozy90/products/5569306460", "맥주");
//        Product product5 = new Product(5L, "https://image.wingeat.com/item/main/f325cb3994da4a0e88c53c3e36332704-w600-v2.jpg", "땅콩버터구이 오징어", "2,790", "https://www.wingeat.com/item/favoreatgrilledsquid", "맥주");
//        Product product6 = new Product(6L, "https://shop-phinf.pstatic.net/20200921_261/1600694260144VlRsj_JPEG/38056593735283057_296908942.jpg?type=m1000", "밀푀유나베", "19,900", "https://brand.naver.com/fresheasy/products/586183550", "양주");
//        Product product7 = new Product(7L, "http://gdimg.gmarket.co.kr/2519006963/still/600?ver=1662518369", "노브랜드 타코야끼400g", "4,980", "http://item.gmarket.co.kr/Item?goodscode=2519006963", "맥주");
//        Product product8 = new Product(8L, "https://shop-phinf.pstatic.net/20220511_4/1652217578802Xw2Uu_JPEG/53353424330968896_1666733552.jpg?type=m510", "구워먹는치즈 임실치즈 와인안주 200g", "12,900", "https://smartstore.naver.com/sunmoon_market/products/6659996769?NaPm=ct%3Dl75xccrs%7Cci%3Dfa6a70276eb79c2a9878868a4ef07a32ff15f694%7Ctr%3Dsls%7Csn%3D1197806%7Chk%3D3074d2b34d9c7df99e4fca00234577c82852e0c6", "와인");
//        productRepository.save(product1);
//        productRepository.save(product2);
//        productRepository.save(product3);
//        productRepository.save(product4);
//        productRepository.save(product5);
//        productRepository.save(product6);
//        productRepository.save(product7);
//        productRepository.save(product8);
//
//    }
//
//    private void createData(int count, User testUser1) {
//        int leftLimit = 97; // letter 'a'
//        int rightLimit = 122; // letter 'z'
//        int targetStringLength = 3;
//
//        for (int i = 0; i < count; i++) {
//            Random random = new Random();
//
//            String title = random.ints(leftLimit, rightLimit + 1)
//                    .limit(targetStringLength)
//                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                    .toString();
//
//            String content = random.ints(leftLimit, rightLimit + 1)
//                    .limit(targetStringLength)
//                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                    .toString();
//
//            int subjectNumber = random.nextInt(6) + 1;
//            String alcoholTag;
//
//            switch (subjectNumber) {
//                case 1:
//                    alcoholTag = "소주";
//                    break;
//                case 2:
//                    alcoholTag = "맥주";
//                    break;
//                case 3:
//                    alcoholTag = "와인";
//                    break;
//                case 4:
//                    alcoholTag = "막걸리";
//                    break;
//                case 5:
//                    alcoholTag = "양주";
//                    break;
//                case 6:
//                    alcoholTag = "전통주";
//                    break;
//                case 7:
//                    alcoholTag = "기타";
//                    break;
//                default:
//                    alcoholTag = null;
//                    break;
//
//            }
//
//            User user = testUser1;
//
//            String freetag = random.ints(leftLimit, rightLimit + 1)
//                    .limit(targetStringLength)
//                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                    .toString();
//
////            String thumbnailImgUrl = "https://bucketservice.s3.ap-northeast-2.amazonaws.com/homeTmate/roomdefault.png";
//            String thumbnailImgUrl = "https://s3.ap-northeast-2.amazonaws.com/bucketservice/roomDefault.png";
//
//            Tables alcoholTable = new Tables(title, content, alcoholTag, freetag, thumbnailImgUrl, user);
//
//            tablesRepository.save(alcoholTable);
//
//        }
//    }
//}
