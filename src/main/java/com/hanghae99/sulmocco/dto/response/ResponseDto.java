package com.hanghae99.sulmocco.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  //null인 데이터는 json 결과에 나오지 않음
@ToString
public class ResponseDto {

    private boolean response;
    private String message;
    private String id;
    private String username;

    public ResponseDto (boolean response, String message) {
        this.response = response;
        this.message = message;
    }
    public ResponseDto (boolean response, String id, String username) {
        this.response = response;
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "response=" + response +
                ", message='" + message + '\'' +
                '}';
    }
}
