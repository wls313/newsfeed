package com.fiveman.newsfeed.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class DeleteCommentRequestDto {
    @NotNull(message="덧글ID를 비워둘 수 없습니다.")
    private Long commentId;
}
