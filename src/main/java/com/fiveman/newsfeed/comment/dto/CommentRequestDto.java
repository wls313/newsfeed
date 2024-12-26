package com.fiveman.newsfeed.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CommentRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long commentId;
    @NotEmpty
    private String content;


    public CommentRequestDto(Long userId,String content){
        this.userId=userId;
        this.content = content;
    }
    public CommentRequestDto(Long userId,Long commentId) {
        this.userId=userId;
        this.commentId = commentId;
    }

}
