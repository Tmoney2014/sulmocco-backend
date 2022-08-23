package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.BbatonResponseDto;
import com.hanghae99.sulmocco.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class Bbatoncontroller {

    private final UserService userService;

    // 회원가입 과정에서 성인인증 처리
    @GetMapping(value = "/oauth2/redirect")
    public ResponseEntity<?> redirectV1(@RequestParam String code) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        String credentials = "JDJhJDA0JDMwN05RYjlwMG54UjJFOGZ2Z2JtQmVNRGJPcDFEWHY0UndMUGpu:Rlo3ckdVWEVWY0ZTdXhp";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:3000/oauth2/redirect");

        HttpEntity<MultiValueMap<String, String>> requestParams = new HttpEntity<>(params, headers);
        ResponseEntity response = restTemplate.postForEntity("https://bauth.bbaton.com/oauth/token", requestParams, String.class);
        // 토큰 호출 여부
        if (response.getStatusCode() == HttpStatus.OK) {
            //토큰 호출 성공
            System.out.println("토큰 호출 성공");
            JSONParser parser = new JSONParser();
            JSONObject jsonBody = (JSONObject) parser.parse((String) response.getBody());
            String token_type = (String) jsonBody.get("token_type");
            String accessToken = (String) jsonBody.get("access_token");

            // 토큰으로 사용자 정보 요청
            headers.set("Authorization", token_type + " " + accessToken);
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity certificationsInfo = restTemplate.exchange("https://bapi.bbaton.com/v2/user/me", HttpMethod.GET, entity, String.class);
            if (certificationsInfo.getStatusCode() == HttpStatus.OK) {
                System.out.println("200 OK");
                JSONParser parser2 = new JSONParser();
                JSONObject jsonBody2 = (JSONObject) parser2.parse((String) certificationsInfo.getBody());
                String adultFlag = (String) jsonBody2.get("adult_flag");
                String user_id = (String) jsonBody2.get("user_id");

                // 비바톤ID와 우리 ID가 동일하기 때문에 여기서 이전에 회원가입을 했는지 DB 조회
                userService.checkUserId(user_id);

                if ("Y".equals(adultFlag)) {
                    //인증 성공
                    return new ResponseEntity<>(new BbatonResponseDto(true, user_id), HttpStatus.valueOf(200));
                } else {
                    //인증 실패 처리
                    return new ResponseEntity<>(new BbatonResponseDto(false, "미성년자입니다"), HttpStatus.valueOf(400));
                }
            } else {
                return new ResponseEntity<>("오류가 발생했습니다", HttpStatus.valueOf(401));
            }

        }
        return new ResponseEntity<>("인증에 성공하였습니다.", HttpStatus.valueOf(200));
    }

    //비밀번호 수정
    @GetMapping(value = "/oauth2/redirect_pw")
    public ResponseEntity<?> redirectV2(@RequestParam String code) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        String credentials = "JDJhJDA0JFRObG05TnRXOE9VcU9ZdEQ5R1Rrb2VtWlYyb2taVHNOelRIUTBj:d3BNdnBYUTVta1ZXRFB1";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:3000/oauth2/redirect");

        HttpEntity<MultiValueMap<String, String>> requestParams = new HttpEntity<>(params, headers);
        ResponseEntity response = restTemplate.postForEntity("https://bauth.bbaton.com/oauth/token", requestParams, String.class);
        // 토큰 호출 여부
        if (response.getStatusCode() == HttpStatus.OK) {
            //토큰 호출 성공
            System.out.println("토큰 호출 성공");
            JSONParser parser = new JSONParser();
            JSONObject jsonBody = (JSONObject) parser.parse((String) response.getBody());
            String token_type = (String) jsonBody.get("token_type");
            String accessToken = (String) jsonBody.get("access_token");

            // 토큰으로 사용자 정보 요청
            headers.set("Authorization", token_type + " " + accessToken);
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity certificationsInfo = restTemplate.exchange("https://bapi.bbaton.com/v2/user/me", HttpMethod.GET, entity, String.class);
            if (certificationsInfo.getStatusCode() == HttpStatus.OK) {
                System.out.println("200 OK");
                JSONParser parser2 = new JSONParser();
                JSONObject jsonBody2 = (JSONObject) parser2.parse((String) certificationsInfo.getBody());
                String adultFlag = (String) jsonBody2.get("adult_flag");
                String user_id = (String) jsonBody2.get("user_id");

                // 비바톤ID와 우리 ID가 동일하기 때문에 여기서 이전에 회원가입을 했는지 DB 조회
                userService.checkUserId(user_id);

                if ("Y".equals(adultFlag)) {
                    //인증 성공
                    return new ResponseEntity<>(new BbatonResponseDto(true, user_id), HttpStatus.valueOf(200));
                } else {
                    //인증 실패 처리
                    return new ResponseEntity<>(new BbatonResponseDto(false, "미성년자입니다"), HttpStatus.valueOf(400));
                }
            } else {
                return new ResponseEntity<>("오류가 발생했습니다", HttpStatus.valueOf(401));
            }

        }
        return new ResponseEntity<>("인증에 성공하였습니다.", HttpStatus.valueOf(200));
    }
}
