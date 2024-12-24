package com.fiveman.newsfeed.user.dto;

public record SignupRequestDto(
        String username,
        String email,
        String password,
        Short age) {
}