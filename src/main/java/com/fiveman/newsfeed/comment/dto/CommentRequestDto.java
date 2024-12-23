package com.fiveman.newsfeed.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long commentId;
    private String content;


    public CommentRequestDto(String content){
        this.content = content;
    }
    public CommentRequestDto(Long commentId) {
        this.commentId = commentId;
    }

}
