package com.fiveman.newsfeed.comment.service;

import com.fiveman.newsfeed.board.repository.BoardRepository;
import com.fiveman.newsfeed.comment.dto.CommentResponseDto;
import com.fiveman.newsfeed.comment.dto.CommentServiceRequestDto;
import com.fiveman.newsfeed.comment.repository.CommentRepository;
import com.fiveman.newsfeed.common.entity.Board;
import com.fiveman.newsfeed.common.entity.Comment;
import com.fiveman.newsfeed.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    CommentRepository commentRepository;
    UserRepository userRepository;
    BoardRepository boardRepository;

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



    }
