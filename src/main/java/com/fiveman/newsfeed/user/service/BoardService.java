package com.fiveman.newsfeed.user.service;

import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.common.entity.User;  // User 클래스 임포트
import com.fiveman.newsfeed.user.dto.BoardRequestDto;
import com.fiveman.newsfeed.user.repository.BoardRepository;
import com.fiveman.newsfeed.user.repository.UserRepository;  // UserRepository 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;  // UserRepository 의존성 추가


    public Board createBoard(Long id, String title, String contents) {

        // Board 객체 생성
        Board board = new Board(title, contents);
        return boardRepository.save(board);
    }

    // 모든 게시물 조회 메서드 추가
    public List<Board> getAllBoards() {
        return boardRepository.findAll();  // BoardRepository에서 findAll() 메서드를 호출하여 모든 게시물 반환
    }
    // 게시글 수정 메서드 추가
    public Board updateBoard(Long boardId, BoardRequestDto dto) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setTitle(dto.title());  // 제목 수정
            board.setContent(dto.contents());  // 내용 수정
            return boardRepository.save(board);  // 수정된 게시글 저장
        } else {
            throw new RuntimeException("Board not found with id: " + boardId);
        }
    }
}