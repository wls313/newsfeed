package com.fiveman.newsfeed.comment.service;

import com.fiveman.newsfeed.board.repository.BoardRepository;
import com.fiveman.newsfeed.comment.dto.CommentResponseDto;
import com.fiveman.newsfeed.comment.dto.CommentServiceRequestDto;
import com.fiveman.newsfeed.comment.repository.CommentRepository;
import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.common.entity.Comment;
import com.fiveman.newsfeed.common.entity.Like;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.like.LikeRepository;
import com.fiveman.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    public List<CommentResponseDto> findByAllComment(CommentServiceRequestDto commentServiceRequestDto){
        return CommentResponseDto.of(commentRepository.findAll());
    }

    public CommentResponseDto createCommentByboardId(CommentServiceRequestDto commentServiceRequestDto){
        User user = userRepository.findById(commentServiceRequestDto.getUserId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Board board = boardRepository.findById(commentServiceRequestDto.getBoardId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Comment comment = new Comment(user,board,commentServiceRequestDto.getContent());
        return CommentResponseDto.of(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponseDto updateCommentByboardId(CommentServiceRequestDto commentServiceRequestDto){

            Comment comment = commentRepository.findById(commentServiceRequestDto.getCommentId()).orElseThrow(()
                    -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            if(comment.getUser().getUserId().equals(commentServiceRequestDto.getUserId())){
                comment.updateContent(commentServiceRequestDto.getContent());
                return CommentResponseDto.of(comment);
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

    }

    public boolean deleteCommentByboardId(CommentServiceRequestDto commentServiceRequestDto){

            Board board = boardRepository.findById(commentServiceRequestDto.getBoardId()).orElseThrow(()
                    -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            User boardUser = userRepository.findById(board.getUser().getUserId()).orElseThrow(()
                    -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            Comment comment = commentRepository.findById(commentServiceRequestDto.getCommentId()).orElseThrow(()
                                                          -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            if(comment.getUser().getUserId()==boardUser.getUserId()||comment.getUser().getUserId()==commentServiceRequestDto.getUserId()){
                commentRepository.deleteById(commentServiceRequestDto.getCommentId());
                return true;
            }else {
                return false;
            }

    }

    @Transactional
    public void likeComment(Long boardId, Long commentId, Long userId) {
            Comment comment = commentRepository.findByCommentIdAndBoard_BoardId(commentId, boardId).orElseThrow(() -> new IllegalArgumentException());
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());

            Like like = new Like(comment, user);
            likeRepository.save(like);
            comment.like(user);
    }

    @Transactional
    public void unlikeComment(Long boardId, Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndBoard_BoardId(commentId, boardId).orElseThrow(() -> new IllegalArgumentException());
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
        Like like = new Like(comment, user);

        if(likeRepository.existsById(like.getLikeId())) {
            likeRepository.deleteById(like.getLikeId());
            comment.unlike(user);
        }

    }
}
