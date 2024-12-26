package com.fiveman.newsfeed.board.controller;

import com.fiveman.newsfeed.board.dto.BoardResponseDto;
import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.board.dto.BoardRequestDto;
import com.fiveman.newsfeed.board.service.BoardService;
import com.fiveman.newsfeed.like.dto.LikeBoardRequestDto;
import com.fiveman.newsfeed.like.dto.LikeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto dto) {
        return new ResponseEntity<>(boardService.createBoard(dto.id(), dto.title(), dto.contents()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BoardResponseDto>> getBoards(@PageableDefault(size = 10) Pageable pageable,
                                                            @RequestParam(required = false) Long userId,
                                                            @RequestParam(required = false) String title,
                                                            @RequestParam(required = false) String sort,
                                                            @RequestParam(required = false) LocalDate startDate,
                                                            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(boardService.getBoards(pageable, userId, title, sort, startDate, endDate));
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto dto) {
        Board updatedBoard = boardService.updateBoard(boardId, dto.title(), dto.contents());
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }

    @PostMapping("/{boardId}/like")
    public ResponseEntity<LikeResponseDto> likeBoard(@PathVariable Long boardId, @RequestBody LikeBoardRequestDto dto) {
        return new ResponseEntity<>(boardService.likeBoard(boardId, dto.userId()), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/unlike")
    public ResponseEntity<LikeResponseDto> unlikeBoard(@PathVariable Long boardId, @RequestBody LikeBoardRequestDto dto) {

        return new ResponseEntity<>(boardService.unlikeBoard(boardId, dto.userId()), HttpStatus.OK);
    }

}