package com.fiveman.newsfeed.user.service;

import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.common.entity.User;  // User 클래스 임포트
import com.fiveman.newsfeed.user.dto.BoardRequestDto;
import com.fiveman.newsfeed.user.repository.BoardRepository;
import com.fiveman.newsfeed.user.repository.UserRepository;  // UserRepository 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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


}