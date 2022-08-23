package com.hanghae99.sulmocco.model;

import com.hanghae99.sulmocco.base.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Friends extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friends_id")
    private Long id;

    @Column(nullable = false)
    private Long addFriendId;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    //== 연관관계 (편의) 메서드==//
    public void setUser(User user) {
        this.user = user;
    }


    public Friends(Long userId, User user) {
        this.addFriendId = userId;
        this.user = user;

    }
}
