package com.fiveman.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String username;
    private final String email;
    private final String password;
    private final Short age;

    public UserRequestDto(String username, String email, String password, Short age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}
