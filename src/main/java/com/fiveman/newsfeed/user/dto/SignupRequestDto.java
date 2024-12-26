package com.fiveman.newsfeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupRequestDto(

        @NotBlank
        String username,

        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "올바른 이메일 형식을 입력하세요.")
        String email,

        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 하나 이상 포함해야 합니다."
        )
        String password,
        Short age ) {
}