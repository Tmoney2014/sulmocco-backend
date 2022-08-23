package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.repository.BannerRepository;
import com.hanghae99.sulmocco.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final ProductRepository productRepository;
    private final BannerRepository bannerRepository;

    // 이 상품 어때요?
    @GetMapping("/api/products")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    // 배너 조회
    @GetMapping("/api/banners")
    public ResponseEntity<?> getBanners() {
        return ResponseEntity.ok().body(bannerRepository.findAll());
    }
}
