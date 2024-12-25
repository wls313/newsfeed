package com.fiveman.newsfeed.friend.controller;

import com.fiveman.newsfeed.friend.dto.FriendListResponseDto;
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

    @GetMapping("/friendsList/{fromUserId}")
    public ResponseEntity<List<FriendListResponseDto>> findAllFriends(@PathVariable Long fromUserId){
        List<FriendListResponseDto> friendListResponseDtos = friendService.findAllFriends(fromUserId);

        return new ResponseEntity<>(friendListResponseDtos,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody FriendRequestDto requestDto){
        friendService.deleteFriend(requestDto.fromUserId(), requestDto.toUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/requests/{fromUserId}")
    public ResponseEntity<Void> acceptFriendRequest(
            @PathVariable Long fromUserId,
            @RequestParam Long myId) {

        friendService.acceptFriendRequest(fromUserId, myId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/requests/{fromUserId}")
    public ResponseEntity<Void> deleteFriendRequest(
            @PathVariable Long fromUserId,
            @RequestParam Long myId) {

        friendService.deleteFriendRequest(fromUserId, myId);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
