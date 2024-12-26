package com.fiveman.newsfeed.comment.controller;

import com.fiveman.newsfeed.auth.service.AuthService;
import com.fiveman.newsfeed.comment.dto.*;
import com.fiveman.newsfeed.comment.service.CommentService;
import com.fiveman.newsfeed.like.dto.LikeCommentRequestDto;
import com.fiveman.newsfeed.like.dto.LikeResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class CommentController {
    private final AuthService authService;
    private final CommentService commentService;

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findByAllComment(@PathVariable @NotNull(message="게시물ID는 빈 값일 수 없습니다.") Long boardId) {

        return new ResponseEntity<>(commentService.findByAllComment(
                new CommentServiceRequestDto(
                        boardId)), HttpStatus.OK);
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> createCommentByboardId(@PathVariable @NotNull(message="게시물ID는 빈 값일 수 없습니다.") Long boardId, @RequestBody @Valid CreateCommentRequestDto commentRequestDto) {


        return new ResponseEntity<>(commentService.createCommentByboardId(
                //덧글 서비스 리퀘스트 dto를 생성합니다.
                new CommentServiceRequestDto(
                        authService.getLoginUserId(),
                        boardId,
                        commentRequestDto.getContent())),
                //생성된 값이 반환되면 httpstatus값으로 201 created를 반환합니다.
                HttpStatus.CREATED);
    }

    @PatchMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> updateCommentByboardId(@PathVariable @NotNull(message="게시물ID는 빈 값일 수 없습니다.") Long boardId, @RequestBody @Valid UpdateCommentRequestDto commentRequestDto) {


        return new ResponseEntity<>(commentService.updateCommentByboardId(

                //덧글 서비스 리퀘스트 dto를 생성합니다.
                new CommentServiceRequestDto(
                        authService.getLoginUserId(),
                        boardId,
                        commentRequestDto.getCommentId(),
                        commentRequestDto.getContent())),
                //생성된 값이 반환되면 httpstatus값으로 200 OK를 반환합니다.
                HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentByboardId(@PathVariable @NotNull(message="게시물ID는 빈 값일 수 없습니다.") Long boardId, @PathVariable @NotNull(message="덧글ID를 비워둘 수 없습니다.") Long commentId) {


        commentService.deleteCommentByboardId( new CommentServiceRequestDto(
                authService.getLoginUserId(),
                boardId,
                commentId)); //덧글 서비스 리퀘스트 dto를 생성합니다.

            return new ResponseEntity<>(HttpStatus.OK);


    }

    @PostMapping("/{boardId}/comments/like")
    public ResponseEntity<LikeResponseDto> likeComment(@PathVariable Long boardId, @RequestBody LikeCommentRequestDto dto) {
        Long myId = authService.getLoginUserId();

        return new ResponseEntity<>(commentService.likeComment(boardId, dto.commentId(), myId), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/comments/unlike")
    public ResponseEntity<LikeResponseDto> unlikeComment(@PathVariable Long boardId, @RequestBody LikeCommentRequestDto dto) {
        Long myId = authService.getLoginUserId();

        return new ResponseEntity<>(commentService.unlikeComment(boardId, dto.commentId(), myId), HttpStatus.OK);
    }


}

