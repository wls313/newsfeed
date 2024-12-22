package com.fiveman.newsfeed.friend.controller;

import com.fiveman.newsfeed.friend.dto.FriendRequestDto;
import com.fiveman.newsfeed.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody FriendRequestDto request) {

        friendService.create(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
