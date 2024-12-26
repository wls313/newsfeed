package com.fiveman.newsfeed.board.repository;

import com.fiveman.newsfeed.board.dto.BoardResponseDto;
import com.fiveman.newsfeed.common.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);

    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b LEFT JOIN b.user u "
            + "ORDER BY b.updatedAt DESC")
    Page<BoardResponseDto> findByOrderByUpdatedAtDesc(Pageable pageable);
}