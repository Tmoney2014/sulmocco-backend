package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.ChangeRequestDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.dto.SignUpRequestDto;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 닉네임 중복 확인
    @GetMapping("/api/checkUser/{username}")
    public ResponseEntity<?> checkNickName(@PathVariable String username) {
        return userService.checkUsername(username);
    }

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return userService.signup(signUpRequestDto);
    }


    //비밀번호 수정
    @PutMapping("/api/resetPw")
    public ResponseEntity<?> changePw(@RequestBody ChangeRequestDto changeRequestDto) {
        return userService.changePw(changeRequestDto);
    }

    //마이 페이지 접속
    @GetMapping("/api/mypage")
    public ResponseEntity<?> mypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.mypage(userDetails);
    }

    // 회원정보 수정
    @PutMapping("/api/mypage")
    public ResponseEntity<?> changeUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ChangeRequestDto changeRequestDto) {
        return userService.changeUser(userDetails, changeRequestDto);

    }

}
