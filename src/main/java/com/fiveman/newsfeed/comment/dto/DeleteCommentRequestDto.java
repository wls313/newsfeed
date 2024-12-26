package com.fiveman.newsfeed.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteCommentRequestDto {
    @NotNull(message="유저ID를 비워둘 수 없습니다.")
    private Long userId;
    @NotNull(message="덧글ID를 비워둘 수 없습니다.")
    private Long commentId;
}
