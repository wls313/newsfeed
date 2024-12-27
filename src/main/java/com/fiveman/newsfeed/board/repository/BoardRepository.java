package com.fiveman.newsfeed.board.repository;

import com.fiveman.newsfeed.board.dto.BoardResponseDto;
import com.fiveman.newsfeed.common.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public interface BoardRepository extends JpaRepository<Board, Long> {

    default Board findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."));
    }


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b INNER JOIN b.user u "
            + "WHERE b.user.userId = :userId" )
    Page<BoardResponseDto> findByUserId(Long userId, Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b INNER JOIN b.user u "
            + "WHERE b.title like concat('%', :title , '%')" )
    Page<BoardResponseDto> findByTitle(String title, Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b INNER JOIN b.user u "
            + "ORDER BY b.likeCount DESC" )
    Page<BoardResponseDto> findByOrderByLikeCountDesc(Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b INNER JOIN b.user u "
            + "WHERE b.createAt BETWEEN :startDate AND :endDate" )
    Page<BoardResponseDto> findByperiod(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b INNER JOIN b.user u "
            + "ORDER BY b.updatedAt DESC")
    Page<BoardResponseDto> findByOrderByUpdatedAtDesc(Pageable pageable);


    @Query(value = "SELECT new com.fiveman.newsfeed.board.dto.BoardResponseDto(b.boardId, b.title, b.content, b.likeCount, b.createAt, b.updatedAt, u.email, u.username) "
            + "FROM Board b "
            + "INNER JOIN b.user u "
            + "INNER JOIN Friend f ON u.userId=f.toUser.userId "
            + "WHERE f.fromUser.userId = :myId AND f.status= 'ACCEPTED'"
            + "ORDER BY b.updatedAt DESC")
    Page<BoardResponseDto> findByFriends(Long myId, Pageable pageable);

}