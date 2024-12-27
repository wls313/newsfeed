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
import com.fiveman.newsfeed.like.dto.LikeResponseDto;
import com.fiveman.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public List<CommentResponseDto> findByAllComment(CommentServiceRequestDto commentServiceRequestDto) {
        Board board = boardRepository.findById(commentServiceRequestDto.getBoardId()).orElseThrow(()
                -> new IllegalArgumentException("게시글이 데이터베이스에 없습니다."));
        return CommentResponseDto.of(commentRepository.findByBoard(board));
    }

    public CommentResponseDto createCommentByboardId(CommentServiceRequestDto commentServiceRequestDto) {
        User user = userRepository.findById(commentServiceRequestDto.getUserId()).orElseThrow(()
                -> new IllegalArgumentException("유저아이디가 데이터베이스에 없습니다."));
        Board board = boardRepository.findById(commentServiceRequestDto.getBoardId()).orElseThrow(()
                -> new IllegalArgumentException("게시글이 데이터베이스에 없습니다."));
        Comment comment = new Comment(user,commentServiceRequestDto.getContent());
        board.addComment(comment);
        return CommentResponseDto.of(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponseDto updateCommentByboardId(CommentServiceRequestDto commentServiceRequestDto) {

        Comment comment = commentRepository.findByCommentIdAndBoard_BoardId(commentServiceRequestDto.getCommentId(), commentServiceRequestDto.getBoardId()).orElseThrow(()
                -> new IllegalArgumentException("댓글이나 게시글이 데이터베이스에 없습니다."));
        if (comment.getUser().getUserId().equals(commentServiceRequestDto.getUserId())) {
            comment.updateContent(commentServiceRequestDto.getContent());
            return CommentResponseDto.of(comment);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "댓글작성자 본인만 수정이 가능합니다.");
        }

    }

    public void deleteCommentByboardId(CommentServiceRequestDto commentServiceRequestDto) {

        Board board = boardRepository.findById(commentServiceRequestDto.getBoardId()).orElseThrow(()
                -> new IllegalArgumentException("게시글이 데이터베이스에 없습니다."));
        User boardUser = userRepository.findById(board.getUser().getUserId()).orElseThrow(()
                -> new IllegalArgumentException("게시글의 작성자가 데이터베이스에 없습니다."));
        Comment comment = commentRepository.findById(commentServiceRequestDto.getCommentId()).orElseThrow(()
                -> new IllegalArgumentException("댓글이 데이터베이스에 없습니다."));
        if (boardUser.getUserId() == commentServiceRequestDto.getUserId() || comment.getUser().getUserId() == commentServiceRequestDto.getUserId()) {
            commentRepository.deleteById(commentServiceRequestDto.getCommentId());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제할 권한을 가진 사용자가 아닙니다.");
        }

    }

    @Transactional
    public LikeResponseDto likeComment(Long boardId, Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndBoard_BoardId(commentId, boardId).orElseThrow(() -> new IllegalArgumentException("댓글이나 게시글이 데이터베이스에 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저아이디가 데이터베이스에 없습니다."));

        Like like = new Like(comment, user);

        if (likeRepository.existsById(like.getLikeId())) {
            throw new IllegalArgumentException("댓글 당 좋아요는 한번만 누를 수 있습니다.");
        }

        likeRepository.save(like);
        comment.like(user);

        return new LikeResponseDto("댓글 좋아요에 성공했습니다", comment.getLikeCount());
    }

    @Transactional
    public LikeResponseDto unlikeComment(Long boardId, Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndBoard_BoardId(commentId, boardId).orElseThrow(() -> new IllegalArgumentException("댓글이나 게시글이 데이터베이스에 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저아이디가 데이터베이스에 없습니다."));
        Like like = new Like(comment, user);

        if (likeRepository.existsById(like.getLikeId())) {
            likeRepository.deleteById(like.getLikeId());
            comment.unlike(user);
        } else {
            throw new IllegalArgumentException("이미 댓글 좋아요가 취소되었거나 좋아요를 누른적이 없습니다.");
        }

        return new LikeResponseDto("댓글 좋아요를 취소했습니다", comment.getLikeCount());
    }
}
