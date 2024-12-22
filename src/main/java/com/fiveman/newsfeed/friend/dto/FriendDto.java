package com.fiveman.newsfeed.friend.dto;


import com.fiveman.newsfeed.common.entity.Friend;
import com.fiveman.newsfeed.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FriendDto {
    User fromUser;
    User toUser;
    String status;

    public static FriendDto from(Friend friend) {
        return new FriendDto(
                friend.getFromUser(),
                friend.getToUser(),
                friend.getStatus()
        );
    }
}
