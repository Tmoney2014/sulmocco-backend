package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.ChangeRequestDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.dto.SignUpRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import com.hanghae99.sulmocco.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 닉네임 중복 확인
    @GetMapping("/api/checkUser/{username}")
    public ResponseEntity<?> checkNickName(@PathVariable String username) {
//        return ResponseEntity.ok().body(userService.checkNickName(nickName), );
        return userService.checkUsername(username);
    }

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return userService.signup(signUpRequestDto);
    }

    // 리프레쉬 토큰 재발급 API
    @PutMapping("/api/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return userService.refreshToken(request);
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

    @GetMapping("/api/getUser")
    public ResponseEntity<?> getUser (@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getUser(userDetails);
    }

    @DeleteMapping("/api/deleteUser")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return userService.deleteUser(user);
    }
}
