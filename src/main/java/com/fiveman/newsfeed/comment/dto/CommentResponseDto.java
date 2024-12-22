package com.fiveman.newsfeed.comment.dto;

import com.fiveman.newsfeed.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class CommentResponseDto {
    private Long userid;
    private Long commentId;
    private String content;
    private LocalDate createAt;
    private LocalDate updatedAt;

    public static List<CommentResponseDto> of(List<Comment> comment) {
        List<CommentResponseDto> target = new ArrayList<>();
        for (Comment sourceComment : comment) {
            target.add(new CommentResponseDto(
                    sourceComment.getUser().getUserId(),
                    sourceComment.getCommentId(),
                    sourceComment.getContent(),
                    sourceComment.getCreateAt(),
                    sourceComment.getUpdatedAt()
            ));
        }
        return target;
    }

    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(
                comment.getUser().getUserId(),
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreateAt(),
                comment.getUpdatedAt()
        );
    }
}
