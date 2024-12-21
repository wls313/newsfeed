package com.fiveman.newsfeed.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CommentServiceRequestDto {
    private Long userId;
    private Long boardId;
    private Long commentId;
    private String content;

    // 덧글 추가를 위해 유저아이디, 보드아이디, 덧글 내용을 받습니다.
    public CommentServiceRequestDto(Long userId,Long boardId,String content){

    }

    // 덧글 조회를 위해 보드아이디를 받습니다.
    public CommentServiceRequestDto(Long boardId){

    }


    //덧글 삭제를 위해 유저아이디, 보드아이디, 덧글아이디를 받습니다.
    public CommentServiceRequestDto(Long userId,Long boardId,Long commentId){

    }

    //수정은 4개의 필드에 모두 값을 할당하기 때문에 AllArgsConstructor로 해결됩니다.

}
