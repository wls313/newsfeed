package com.fiveman.newsfeed.board.dto;

public record CreateBoardResponseDto(Long boardId, String title, String content, Integer likeCount, String email, String username) {
}
