package com.hanghae99.sulmocco.controller;

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

import javax.servlet.http.HttpServletRequest;

@RestController
public class controller {
    //API신청시의 Redirect uri : http://{REDIRECT_URI}?code={CODE}
    @GetMapping(value = "/oauth2/redirect?code={CODE}")
    public ResponseEntity<?> redirect(HttpServletRequest request, @RequestParam String code) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        String credentials = "JDJhJDA0JDMwN05RYjlwMG54UjJFOGZ2Z2JtQmVNRGJPcDFEWHY0UndMUGpu:Rlo3ckdVWEVWY0ZTdXhp";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
        System.out.println("String encodedCredentials : " + encodedCredentials);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);
        System.out.println("headers : " + headers.toString());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:3000/oauth2/redirect");

        HttpEntity<MultiValueMap<String, String>> requestParams = new HttpEntity<>(params, headers);
        ResponseEntity response = restTemplate.postForEntity("https://bauth.bbaton.com/oauth/token", requestParams, String.class);
        System.out.println("post 요청 완료");
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("if 시작");
            //토큰 호출 성공
            JSONParser parser = new JSONParser();
            JSONObject jsonBody = (JSONObject) parser.parse((String) response.getBody());
            String token_type = (String) jsonBody.get("token_type");
            String accessToken = (String) jsonBody.get("access_token");
            System.out.println("if 끝");
//            return "토큰 호출 성공";
//            return new ResponseMessageDto(true, "토큰 호출 성공");
            return new ResponseEntity<>("호출 성공", HttpStatus.valueOf(200));

        } else {
            System.out.println("실패실패");
            //토큰 호출 실패
//            return new ResponseMessageDto(false, "토큰 호출 실패");
            return new ResponseEntity<>("호출 실패", HttpStatus.valueOf(401));
        }
    }
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
