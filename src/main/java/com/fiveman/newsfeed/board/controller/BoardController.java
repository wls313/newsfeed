package com.fiveman.newsfeed.board.controller;

import com.fiveman.newsfeed.auth.service.AuthService;
import com.fiveman.newsfeed.board.dto.BoardResponseDto;
import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.board.dto.BoardRequestDto;
import com.fiveman.newsfeed.board.service.BoardService;
import com.fiveman.newsfeed.like.dto.LikeBoardRequestDto;
import com.fiveman.newsfeed.like.dto.LikeResponseDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final AuthService authService;
    private final BoardService boardService;


    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto dto) {
        Long myId = authService.getLoginUserId();

        return new ResponseEntity<>(boardService.createBoard(myId, dto.title(), dto.contents()), HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId) {
        return new ResponseEntity<>(boardService.getBoard(boardId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BoardResponseDto>> getBoards(@PageableDefault(size = 10) Pageable pageable,
                                                            @RequestParam(required = false) Long userId,
                                                            @RequestParam(required = false) String title,
                                                            @RequestParam(required = false) String filter,
                                                            @RequestParam(required = false) LocalDate startDate,
                                                            @RequestParam(required = false) LocalDate endDate) {
        Long myId = authService.getLoginUserId();

        return ResponseEntity.ok(boardService.getBoards(pageable, myId, userId, title, filter, startDate, endDate));
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto dto) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, dto.title(), dto.contents()));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable  Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }

    @PostMapping("/{boardId}/like")
    public ResponseEntity<LikeResponseDto> likeBoard(@PathVariable Long boardId) {
        Long myId = authService.getLoginUserId();

        return new ResponseEntity<>(boardService.likeBoard(boardId, myId), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/unlike")
    public ResponseEntity<LikeResponseDto> unlikeBoard(@PathVariable Long boardId) {
        Long myId = authService.getLoginUserId();

        return new ResponseEntity<>(boardService.unlikeBoard(boardId, myId), HttpStatus.OK);
    }

}