package com.fiveman.newsfeed.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FriendListResponseDto {
    private Long toUserId;
    private String toUserName;
    private String toUserEmail;
}
