package com.hanghae99.sulmocco.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanghae99.sulmocco.model.Friends;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  //null인 데이터는 json 결과에 나오지 않음
public class FriendsResponseDto {

    private String addFriend_id;

    public FriendsResponseDto(Friends friends) {
        this.addFriend_id = String.valueOf(friends.getAddFriend_id());
    }
}
