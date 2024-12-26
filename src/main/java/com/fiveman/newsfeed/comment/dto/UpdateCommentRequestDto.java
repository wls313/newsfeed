package com.fiveman.newsfeed.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class UpdateCommentRequestDto {
    @NotNull(message="유저ID를 비워둘 수 없습니다.")
    private Long userId;
    @NotNull(message="덧글ID를 비워둘 수 없습니다.")
    private Long commentId;
    @NotEmpty(message="덧글내용은 공백이거나 비워둘 수 없습니다.")
    private String content;


}
