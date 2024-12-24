package com.fiveman.newsfeed.board.controller;

import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.board.dto.BoardRequestDto;
import com.fiveman.newsfeed.board.service.BoardService;
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
    public ResponseEntity<Board> createBoard(@RequestBody BoardRequestDto dto) {
        Board createdBoard = boardService.createBoard(dto.id(), dto.title(), dto.contents());
        return ResponseEntity.ok(createdBoard);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
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

}