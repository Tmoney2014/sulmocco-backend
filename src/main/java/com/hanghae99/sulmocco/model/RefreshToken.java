package com.hanghae99.sulmocco.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id
    @Column(name = "rt_key")
    private String loginId;

    @Column(name = "rt_value")
    private String value;   // 리프레시 토큰 값

    @Builder
    public RefreshToken(String loginId, String value) {
        this.loginId = loginId;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
