package com.fiveman.newsfeed.user.dto;

public record SignupRequestDto(
        String email,
        String password,
        Short age) {

}
