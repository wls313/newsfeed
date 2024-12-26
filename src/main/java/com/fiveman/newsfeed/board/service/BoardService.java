package com.fiveman.newsfeed.board.service;

import com.fiveman.newsfeed.board.dto.BoardResponseDto;
import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.board.repository.BoardRepository; // UserRepository 임포트
import com.fiveman.newsfeed.common.entity.Like;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.like.LikeRepository;
import com.fiveman.newsfeed.like.dto.LikeResponseDto;
import com.fiveman.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 추가된 임포트

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;


    public BoardResponseDto createBoard(Long id, String title, String contents) {
        User user = userService.findById(id);

        // Board 객체 생성
        Board board = new Board(title, contents, user);

        boardRepository.save(board);
        return new BoardResponseDto(board.getBoardId(),
                                    board.getTitle(),
                                    board.getContent(),
                                    board.getLikeCount(),
                                    board.getCreateAt(),
                                    board.getUpdatedAt(),
                                    board.getUser().getEmail(),
                                    board.getUser().getUsername());
    }

    // 모든 게시물 조회 메서드 추가
    public Page<BoardResponseDto> getBoards(Pageable pageable, String title) {
        Page<BoardResponseDto> BoardList = new PageImpl<>(new ArrayList<>());

        if (title != null) {
            BoardList = boardRepository.findByTitle(title, pageable);
        } else {
            BoardList = boardRepository.findByOrderByUpdatedAtDesc(pageable);
        }



        return BoardList;
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

    @Transactional
    public LikeResponseDto likeBoard(Long boardId, Long userId) {
        User user = userService.findById(userId);
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException());

        Like like = new Like(board, user);

        if(likeRepository.existsById(like.getLikeId())) {
            throw new IllegalArgumentException();
        }

        likeRepository.save(like);
        board.like(user);

        return new LikeResponseDto("게시글 좋아요에 성공했습니다", board.getLikeCount());
    }

    @Transactional
    public LikeResponseDto unlikeBoard(Long boardId, Long userId) {
        User user = userService.findById(userId);
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException());

        Like like = new Like(board, user);


        if(likeRepository.existsById(like.getLikeId())) {
            likeRepository.deleteById(like.getLikeId());
            board.unlike(user);
        } else {
            throw new IllegalArgumentException();
        }

        return new LikeResponseDto("게시글 좋아요를 취소했습니다", board.getLikeCount());
    }
}