package com.fiveman.newsfeed.board.dto;

import com.fiveman.newsfeed.common.entity.Board;

import java.time.LocalDateTime;

public record BoardResponseDto(Long boardId, String title, String content, Integer likeCount, LocalDateTime createdAt, LocalDateTime updatedAt, String email, String username ) {


    public BoardResponseDto(Board board) {
        this(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                board.getLikeCount(),
                board.getCreateAt(),
                board.getUpdatedAt(),
                board.getUser().getEmail(),
                board.getUser().getUsername()
        );
    }
}
