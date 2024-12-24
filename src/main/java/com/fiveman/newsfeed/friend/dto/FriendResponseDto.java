package com.fiveman.newsfeed.friend.dto;


import com.fiveman.newsfeed.common.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FriendResponseDto {
    private Long fromUserId;
    private String fromUserName;
    private String status;

    // 서비스에서 결과를 Dto에 담아 보내기 위한 정적 팩토리 메서드
    public static FriendResponseDto from(Friend friend) {
        return new FriendResponseDto(
                friend.getFromUser().getUserId(),
                friend.getFromUser().getUsername(),
                friend.getStatus()
        );
    }
}
