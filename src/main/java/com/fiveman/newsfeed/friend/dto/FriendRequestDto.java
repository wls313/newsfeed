package com.fiveman.newsfeed.friend.dto;


public record FriendRequestDto(
        Long fromUserId,
        Long toUserId
) {
}