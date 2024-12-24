package com.fiveman.newsfeed.board.service;

import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.board.repository.BoardRepository; // UserRepository 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 추가된 임포트

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;



    public Board createBoard(Long id, String title, String contents) {

        // Board 객체 생성
        Board board = new Board(title, contents);
        return boardRepository.save(board);
    }

    // 모든 게시물 조회 메서드 추가
    public List<Board> getAllBoards() {
        return boardRepository.findAll();  // BoardRepository에서 findAll() 메서드를 호출하여 모든 게시물 반환
    }

    @Transactional
    public Board updateBoard(Long boardId, String title, String contents) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with ID: " + boardId));
        board.updateBoard(title, contents); // Board의 메서드를 호출하여 수정
        return board; // 변경된 엔티티는 JPA의 트랜잭션 관리에 의해 자동 저장
    }
    @Transactional
    public void deleteBoard(Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new IllegalArgumentException("Board not found with ID: " + boardId);
        }
        boardRepository.deleteById(boardId); // JPA의 기본 deleteById 메서드 사용
    }
}