package com.fiveman.newsfeed.friend.service;

import com.fiveman.newsfeed.common.entity.Friend;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.friend.dto.FriendListResponseDto;
import com.fiveman.newsfeed.friend.dto.FriendResponseDto;
import com.fiveman.newsfeed.friend.repository.FriendRepository;
import com.fiveman.newsfeed.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;

    @Transactional
    public void create(Long fromUserId, Long toUserId) {

        if(userService.findById(toUserId).isDeleted()) {
            throw new IllegalArgumentException("삭제된 유저입니다");
        }
        isValidSelf(fromUserId, toUserId);

        List<Friend> friendPending = friendRepository.findByStatusIsPendingAndFromUserId(fromUserId,toUserId);
        List<Friend> friendPendingReverse = friendRepository.findByStatusIsPendingAndFromUserId(toUserId,fromUserId);
        List<Friend> friendAccept = friendRepository.findByStatusIsAcceptedAndFromUserId(fromUserId,toUserId);

        if(!friendAccept.isEmpty()) {
            throw new IllegalArgumentException("이미 친구입니다.");
        } else if (!friendPending.isEmpty()) {
            throw new IllegalArgumentException("이미 보내진 요청입니다");
        } else if (!friendPendingReverse.isEmpty()) {
            throw new IllegalArgumentException("이미 상대방이 친구 요청을 보냈습니다");
        }

        User fromUser = userService.findById(fromUserId);
        User toUser = userService.findById(toUserId);

        // 정적 팩토리 메서드를 이용해 Friend 객체 생성 후 DB 저장
        friendRepository.save(Friend.of(fromUser, toUser, "PENDING"));
    }

    public List<FriendResponseDto> findAllRequest(Long myId) {
        return friendRepository.findAllRequest(myId).stream()
                .map(FriendResponseDto::from)
                .toList();
    }

    public List<FriendListResponseDto> findAllFriends(Long fromUserId) {
        List<Friend> friends = friendRepository.findByFromUser_UserId(fromUserId);

        return friends.stream()
                .map(friend -> new FriendListResponseDto(
                        friend.getToUser().getUserId(), friend.getToUser().getUsername(),friend.getToUser().getEmail()
                ))
                .collect(Collectors.toList());
    }

    public void deleteFriend(Long fromUserId, Long toUserId) {

        isValidSelf(fromUserId, toUserId);
        isValid(fromUserId, toUserId);

        List<Friend> friendAccept = friendRepository.findByStatusIsAcceptedAndFromUserId(fromUserId,toUserId);

        if(friendAccept.isEmpty()) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        friendRepository.deleteFriendByFromUserToUser(fromUserId,toUserId);
        friendRepository.deleteFriendByFromUserToUser(toUserId,fromUserId);

    }

    @Transactional
    public void acceptFriendRequest(Long fromUserId, Long toUserId) {

        isValidSelf(fromUserId, toUserId);
        isValid(fromUserId, toUserId);

        User fromUser = userService.findById(fromUserId);
        User toUser = userService.findById(toUserId);
        
        Friend friend = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        friend.setStatus("ACCEPTED");
        
        friendRepository.save(Friend.of(toUser, fromUser, "ACCEPTED"));
    }

    @Transactional
    public void deleteFriendRequest(Long fromUserId, Long toUserId) {

        User fromUser = userService.findById(fromUserId);
        User toUser = userService.findById(toUserId);

        Friend friend = friendRepository.findByFromUserAndToUser(fromUser, toUser);

        if(!friend.getStatus().equals("PENDING")) {
            throw new IllegalArgumentException("잘못된 요청입니다");
        }

        isValidSelf(fromUserId, toUserId);
        isValid(fromUserId, toUserId);

        friendRepository.deleteFriendByFromUserToUser(fromUserId, toUserId);
    }

    public void isValidSelf(Long fromUserId, Long toUserId) {

        if(Objects.equals(fromUserId, toUserId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신에게 요청을 보낼 수 없습니다.");
        }
    }

    public void isValid(Long fromUserId, Long toUserId) {
        if(!friendRepository.existsByFromUser_UserIdAndToUser_UserId(fromUserId, toUserId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "찾을 수 없는 요청입니다.");
        }
    }
}