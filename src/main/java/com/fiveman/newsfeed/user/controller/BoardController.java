package com.fiveman.newsfeed.user.controller;

import com.fiveman.newsfeed.common.entity.Board;  // 임포트 경로 수정
import com.fiveman.newsfeed.user.dto.BoardRequestDto;
import com.fiveman.newsfeed.user.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody BoardRequestDto boardRequestDto, @RequestParam Long userId) {
        Board createdBoard = boardService.createBoard(boardRequestDto, userId);
        return ResponseEntity.ok(createdBoard);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }
}