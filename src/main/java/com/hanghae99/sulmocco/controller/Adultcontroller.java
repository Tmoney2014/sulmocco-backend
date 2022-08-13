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

@RestController
public class Adultcontroller {

    // 회원가입 과정에서 성인인증 처리
    @GetMapping(value = "/oauth2/redirect")
    public ResponseEntity<?> redirectV2(@RequestParam String code) throws ParseException {

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
        if (response.getStatusCode() == HttpStatus.OK) {
            //토큰 호출 성공
            JSONParser parser = new JSONParser();
            JSONObject jsonBody = (JSONObject) parser.parse((String) response.getBody());
            String token_type = (String) jsonBody.get("token_type");
            String accessToken = (String) jsonBody.get("access_token");

            headers.set("Authorization", token_type + " " + accessToken);
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity certificationsInfo = restTemplate.exchange("https://bapi.bbaton.com/v2/user/me", HttpMethod.GET, entity, String.class);
            if (certificationsInfo.getStatusCode() == HttpStatus.OK) {
                JSONParser parser2 = new JSONParser();
                JSONObject jsonBody2 = (JSONObject) parser2.parse((String) certificationsInfo.getBody());
                String adultFlag = (String) jsonBody2.get("adult_flag");
                if ("Y".equals(adultFlag)) {
                    //인증 성공
                    return new ResponseEntity<>("나는 성인", HttpStatus.valueOf(200));
                } else {
                    //인증 실패 처리
                    return new ResponseEntity<>("나는 애기", HttpStatus.valueOf(400));
                }
            } else {
                return new ResponseEntity<>("토큰 호출 과정에 에러가 발생했습니다.", HttpStatus.valueOf(401));
            }

        }
        return new ResponseEntity<>("마지막리턴", HttpStatus.valueOf(401));
    }
}
