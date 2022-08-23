package com.hanghae99.sulmocco.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Banner {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "banner_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String imageUrl;

    @Column(nullable = false, unique = true)
    private String redirectUrl;
    
}
