package com.fiveman.newsfeed.friend.repository;


import com.fiveman.newsfeed.common.entity.Friend;
import com.fiveman.newsfeed.common.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("SELECT f FROM Friend f WHERE f.toUser.userId = :toUserId " +
            "AND f.status = 'PENDING' " +
            "AND f.fromUser.isDeleted = false")
    List<Friend> findAllRequest(@Param("toUserId") Long toUserId);

    @Query("SELECT f FROM Friend f WHERE f.fromUser.userId = :fromUserId " +
            "AND f.status = 'ACCEPTED' " +
            "AND f.toUser.isDeleted = false")
    List<Friend> findByFromUser_UserId(Long fromUserId);

    @Query("SELECT f FROM Friend f WHERE f.fromUser.userId = :fromUserId " +
            "AND f.status = 'ACCEPTED' AND f.toUser.userId = :toUserId")
    List<Friend> findByStatusIsAcceptedAndFromUserId(Long fromUserId,Long toUserId);

    @Query("SELECT f FROM Friend f WHERE f.fromUser.userId = :fromUserId " +
            "AND f.status = 'PENDING' AND f.toUser.userId = :toUserId")
    List<Friend> findByStatusIsPendingAndFromUserId(Long fromUserId,Long toUserId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Friend f WHERE f.fromUser.userId = :fromUserId AND f.toUser.userId = :toUserId")
    void deleteFriendByFromUserToUser(@Param("fromUserId") Long fromUserId ,@Param("toUserId") Long toUserId);

    Friend findByFromUserAndToUser(User fromUser, User toUser);

    boolean existsByFromUser_UserIdAndToUser_UserId(Long fromUserId, Long toUserId);
}
