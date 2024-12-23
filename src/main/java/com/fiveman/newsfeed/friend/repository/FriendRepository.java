package com.fiveman.newsfeed.friend.repository;


import com.fiveman.newsfeed.common.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("SELECT f FROM Friend f WHERE f.toUser.userId = :toUserId")
    List<Friend> findAllRequest(@Param("toUserId") Long toUserId);
}
