package com.hanghae99.sulmocco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae99.sulmocco.base.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="talbes_id")
    private Tables tables;

    //== 연관관계 (편의) 메서드==//
    public void setUser(User user) {
        this.user = user;
    }

    public void setTables(Tables tables) {
        this.tables = tables;
    }

    public Reply(String content, User user, Tables tables) {
        this.content = content;
        setUser(user);
        setTables(tables);
    }

    public void update(String content) {
        this.content = content;
    }

}
