package com.fiveman.newsfeed.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CreateCommentRequestDto {
    @NotEmpty(message="덧글내용은 공백이거나 비워둘 수 없습니다.")
    private String content;
}
