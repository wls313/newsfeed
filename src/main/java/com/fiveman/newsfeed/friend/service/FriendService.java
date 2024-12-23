package com.fiveman.newsfeed.friend.service;

import com.fiveman.newsfeed.common.entity.Friend;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.friend.dto.FriendResponseDto;
import com.fiveman.newsfeed.friend.dto.FriendRequestDto;
import com.fiveman.newsfeed.friend.repository.FriendRepository;
import com.fiveman.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;

    public void create(FriendRequestDto request) {

        User fromUser = userService.findById(request.fromUserId());
        User toUser = userService.findById(request.toUserId());

        // 정적 팩토리 메서드를 이용해 Friend 객체 생성 후 DB 저장
        friendRepository.save(Friend.of(fromUser, toUser));
    }

    public List<FriendResponseDto> findAllRequest(Long myId) {
        return friendRepository.findAllRequest(myId).stream()
                .map(FriendResponseDto::from)
                .toList();
    }
}
