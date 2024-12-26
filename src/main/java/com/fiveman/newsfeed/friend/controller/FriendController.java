package com.fiveman.newsfeed.friend.controller;

import com.fiveman.newsfeed.auth.service.AuthService;
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
    private final AuthService authService;

    @PostMapping("/{toUserId}")
    public ResponseEntity<Void> create(@PathVariable Long toUserId) {

        Long loginUserId = authService.getLoginUserId();

        friendService.create(loginUserId, toUserId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FriendResponseDto>> findAllRequests() {

        Long loginUserId = authService.getLoginUserId();

        List<FriendResponseDto> response = friendService.findAllRequest(loginUserId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/friendsList")
    public ResponseEntity<List<FriendListResponseDto>> findAllFriends(){

        Long loginUserId = authService.getLoginUserId();

        List<FriendListResponseDto> friendListResponseDto = friendService.findAllFriends(loginUserId);

        return new ResponseEntity<>(friendListResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{toUserId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long toUserId){

        Long loginUserId = authService.getLoginUserId();

        friendService.deleteFriend(loginUserId, toUserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/requests/{fromUserId}")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long fromUserId) {

        Long loginUserId = authService.getLoginUserId();

        friendService.acceptFriendRequest(fromUserId, loginUserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/requests/{fromUserId}")
    public ResponseEntity<Void> deleteFriendRequest(
            @PathVariable Long fromUserId) {

        Long loginUserId = authService.getLoginUserId();

        friendService.deleteFriendRequest(fromUserId, loginUserId);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
