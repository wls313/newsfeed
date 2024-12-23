package com.fiveman.newsfeed.user.repository;

import com.fiveman.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}