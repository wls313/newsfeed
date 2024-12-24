package com.fiveman.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String username;
    private final String email;
    private final String password;
    private final short age;

    public UserRequestDto(String username, String email, String password, short age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}
