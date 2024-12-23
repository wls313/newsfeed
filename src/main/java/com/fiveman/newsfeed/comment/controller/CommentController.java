package com.fiveman.newsfeed.comment.controller;

import com.fiveman.newsfeed.comment.dto.CommentRequestDto;
import com.fiveman.newsfeed.comment.dto.CommentResponseDto;
import com.fiveman.newsfeed.comment.dto.CommentServiceRequestDto;
import com.fiveman.newsfeed.comment.service.CommentService;
import com.fiveman.newsfeed.like.dto.LikeCommentRequestDto;
import com.fiveman.newsfeed.like.dto.LikeResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findByAllComment(@PathVariable Long boardId) {

        return new ResponseEntity<>(commentService.findByAllComment(
                new CommentServiceRequestDto(
                        boardId)), HttpStatus.OK);
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> createCommentByboardId(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto) {


        return new ResponseEntity<>(commentService.createCommentByboardId(
                //덧글 서비스 리퀘스트 dto를 생성합니다.
                new CommentServiceRequestDto(
                        commentRequestDto.getUserId(),
                        boardId,
                        commentRequestDto.getContent())),
                //생성된 값이 반환되면 httpstatus값으로 201 created를 반환합니다.
                HttpStatus.CREATED);
    }

    @PatchMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> updateCommentByboardId(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto) {


        return new ResponseEntity<>(commentService.updateCommentByboardId(

                //덧글 서비스 리퀘스트 dto를 생성합니다.
                new CommentServiceRequestDto(
                        commentRequestDto.getUserId(),
                        boardId,
                        commentRequestDto.getCommentId(),
                        commentRequestDto.getContent())),
                //생성된 값이 반환되면 httpstatus값으로 200 OK를 반환합니다.
                HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/comments")
    public ResponseEntity<Void> deleteCommentByboardId(@PathVariable Long boardId, @RequestBody CommentRequestDto commentRequestDto) {


        boolean deleteResult = commentService.deleteCommentByboardId( //덧글 서비스 리퀘스트 dto를 생성합니다.
                new CommentServiceRequestDto(
                        commentRequestDto.getUserId(),
                        boardId,
                        commentRequestDto.getCommentId()));
        if (deleteResult) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{boardId}/comments/like")
    public ResponseEntity<LikeResponseDto> likeComment(@PathVariable Long boardId, @RequestBody LikeCommentRequestDto dto) {
        return new ResponseEntity<>(commentService.likeComment(boardId, dto.commentId(), dto.userId()), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/comments/unlike")
    public ResponseEntity<LikeResponseDto> unlikeComment(@PathVariable Long boardId, @RequestBody LikeCommentRequestDto dto) {
        return new ResponseEntity<>(commentService.unlikeComment(boardId, dto.commentId(), dto.userId()), HttpStatus.OK);
    }



}

