package com.hanghae99.sulmocco.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanghae99.sulmocco.model.Friends;
import com.hanghae99.sulmocco.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  //null인 데이터는 json 결과에 나오지 않음
public class FriendsResponseDto {

    private String addFriend_id;
    private String username;
    private String profile;
    private boolean isOnair;

//    public FriendsResponseDto(Friends friends) {
//        this.addFriend_id = String.valueOf(friends.getAddFriend_id());
//    }

    public FriendsResponseDto(User user) {
        this.username = user.getUsername();
        this.profile = user.getProfileUrl();
    }
}
