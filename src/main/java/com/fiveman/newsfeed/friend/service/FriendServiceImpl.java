package com.fiveman.newsfeed.friend.service;

import com.fiveman.newsfeed.common.entity.Friend;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.friend.dto.FriendRequestDto;
import com.fiveman.newsfeed.friend.repository.FriendRepository;
import com.fiveman.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final UserService userService;

    @Override
    public void create(FriendRequestDto request) {

        User fromUser = userService.findById(request.fromUserId());
        User toUser = userService.findById(request.toUserId());

        friendRepository.save(Friend.of(fromUser, toUser));
    }
}
