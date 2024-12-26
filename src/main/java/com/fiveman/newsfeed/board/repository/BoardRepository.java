package com.fiveman.newsfeed.board.repository;

import com.fiveman.newsfeed.board.dto.BoardResponseDto;
import com.fiveman.newsfeed.common.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b LEFT JOIN b.user u "
            + "WHERE b.user.userId = :userId" )
    Page<BoardResponseDto> findByUserId(Long userId, Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b LEFT JOIN b.user u "
            + "WHERE b.title = :title" )
    Page<BoardResponseDto> findByTitle(String title, Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b LEFT JOIN b.user u "
            + "ORDER BY b.likeCount DESC" )
    Page<BoardResponseDto> findByOrderByLikeCountDesc(Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b LEFT JOIN b.user u "
            + "WHERE b.createAt BETWEEN :startDate AND :endDate" )
    Page<BoardResponseDto> findByperiod(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b LEFT JOIN b.user u "
            + "ORDER BY b.updatedAt DESC")
    Page<BoardResponseDto> findByOrderByUpdatedAtDesc(Pageable pageable);

}