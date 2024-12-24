package com.fiveman.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserPasswordRequestDto {
    private final String oldPassword;

    private final String newPassword;

    public UserPasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
