package com.fiveman.newsfeed.friend.repository;


import com.fiveman.newsfeed.common.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
