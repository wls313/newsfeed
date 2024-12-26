package com.fiveman.newsfeed.comment.dto;

import com.fiveman.newsfeed.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class CommentResponseDto {
    private Long userid;
    private Long commentId;
    private String content;
    private Integer LikeCount;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public static List<CommentResponseDto> of(List<Comment> comment) {
        List<CommentResponseDto> target = new ArrayList<>();
        for (Comment sourceComment : comment) {
            target.add(new CommentResponseDto(
                    sourceComment.getUser().getUserId(),
                    sourceComment.getCommentId(),
                    sourceComment.getContent(),
                    sourceComment.getLikeCount(),
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
                comment.getLikeCount(),
                comment.getCreateAt(),
                comment.getUpdatedAt()
        );
    }
}
