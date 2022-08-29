package com.hanghae99.sulmocco.service;

import com.hanghae99.sulmocco.dto.MypageResponseDto;
import com.hanghae99.sulmocco.dto.ChangeRequestDto;
import com.hanghae99.sulmocco.dto.ResponseDto;
import com.hanghae99.sulmocco.dto.SignUpRequestDto;
import com.hanghae99.sulmocco.model.User;
import com.hanghae99.sulmocco.repository.UserRepository;
import com.hanghae99.sulmocco.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ResponseEntity<ResponseDto> signup(SignUpRequestDto requestDto) {

        String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto, password);

        if (userRepository.findById(requestDto.getId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        userRepository.save(user);
        return new ResponseEntity<>(new ResponseDto(true, "회원가입이 완료 되었습니다."), HttpStatus.OK);
    }

    public ResponseEntity<?> checkUsername(String username){

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        } else {
//            return new ResponseEntity<>("사용가능한 닉네임 입니다", HttpStatus.valueOf(200));
            return ResponseEntity.ok().body("사용가능한 닉네임 입니다.");
        }
    }

    public ResponseEntity<?> checkUserId(String Id) {

        if (userRepository.findById(Id) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다. 아이디를 찾아주세요.");
        } else {
            return ResponseEntity.ok().body(new ResponseDto(true, "사용 가능한 아이디입니다."));
        }
    }

    public boolean checkUserIdPw(String Id) {

        User user = userRepository.findById(Id);
       if (user == null) {
            return false;
        } else {
            return true;
        }

    }

    // 비밀번호 찾기
    @Transactional
    public ResponseEntity<?> changePw(ChangeRequestDto changeRequestDto) {
        User finduUser = userRepository.findById(changeRequestDto.getId());

        String password = bCryptPasswordEncoder.encode(changeRequestDto.getPassword());

        if (changeRequestDto.getPassword().equals(changeRequestDto.getPassword2())) {

            finduUser.updatePw(password, finduUser);
        } else {
            throw new IllegalArgumentException("비밀번호확인이 일치하지 않습니다.");
        }
        return new ResponseEntity<>("비밀번호 재설정 완료", HttpStatus.valueOf(200));


    }

    public ResponseEntity<?> mypage(UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
//        Long userId = userDetails.getUser().getUserId();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        MypageResponseDto mypageResponseDto = new MypageResponseDto(user);

        return new ResponseEntity<>(mypageResponseDto, HttpStatus.valueOf(200));
    }

    // 회원정보 수정
    @Transactional
    public ResponseEntity<?> changeUser(UserDetailsImpl userDetails, ChangeRequestDto changeRequestDto) {
        String username = userDetails.getUser().getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.updateUser(changeRequestDto);

        return new ResponseEntity<>("회원정보수정 완료", HttpStatus.valueOf(200));
    }
}
