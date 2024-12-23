package com.fiveman.newsfeed.friend.controller;

import com.fiveman.newsfeed.friend.dto.FriendResponseDto;
import com.fiveman.newsfeed.friend.dto.FriendRequestDto;
import com.fiveman.newsfeed.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/requests")
    public ResponseEntity<List<FriendResponseDto>> findAllRequests(@RequestParam Long myId) {

        List<FriendResponseDto> response = friendService.findAllRequest(myId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
