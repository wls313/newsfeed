package com.fiveman.newsfeed.user.repository;

import com.fiveman.newsfeed.common.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}