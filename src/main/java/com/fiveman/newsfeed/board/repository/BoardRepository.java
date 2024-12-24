package com.fiveman.newsfeed.board.repository;

import com.fiveman.newsfeed.common.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
}