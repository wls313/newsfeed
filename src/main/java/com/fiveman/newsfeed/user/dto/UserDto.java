package com.fiveman.newsfeed.user.dto;

import com.fiveman.newsfeed.common.entity.User;

import java.time.LocalDateTime;

public record UserDto(
        Long userId,
        String email,
        Short age,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getUserId(),
                user.getEmail(),
                user.getAge(),
                user.getCreateAt(),
                user.getUpdatedAt()
        );
    }
}
