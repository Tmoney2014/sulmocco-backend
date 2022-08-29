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


    private String username;
    private String profile;
    private boolean isOnair;
    private String level;
    private boolean isfriend;


    public FriendsResponseDto(User user, boolean isOnair ) {
        this.username = user.getUsername();
        this.profile = user.getProfileUrl();
        this.level = user.getLevel();
        this.isOnair = isOnair;
    }

    //TODO ㅠㅠ 이거 두개 어떻게 합치는 방법이 없는건가 ?? !!!
    public FriendsResponseDto(User user, boolean isfriend, String username ){
        this.username = user.getUsername();
        this.profile = user.getProfileUrl();
        this.level = user.getLevel();
        this.isfriend = isfriend;

    }

}
