package com.fiveman.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String username;
    private final String email;
    private final Short age;

    public UserResponseDto(String username, String email, Short age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }
}
