package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.PasswordChangeRequestDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.dto.SignUpRequestDto;
import com.hanghae99.sulmocco.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
//    @GetMapping("/api/checkUser/")

    //비밀번호 수정
    @PutMapping("/api/resetPw")
    public  ResponseEntity<?>  changePw(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto){
        return userService.changePw(passwordChangeRequestDto);



    }
}
