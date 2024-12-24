package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")



public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long userId;

    @Column(unique=true,nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String age;

    @Column
    private boolean isDeleted;

}
