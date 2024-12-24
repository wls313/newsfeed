package com.fiveman.newsfeed.common.entity;

import com.fiveman.newsfeed.like.LikeId;
import com.fiveman.newsfeed.like.LikeTarget;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "likeEntity")
public class Like {

    @EmbeddedId
    private LikeId likeId;

    public Like(Board board, User user) {
        this.likeId = new LikeId(board.getBoardId(), 0L, user.getUserId(), LikeTarget.BOARD.name());
    }

    public Like(Comment comment, User user) {
        this.likeId = new LikeId(comment.getCommentId(), comment.getBoard().getBoardId(), user.getUserId(), LikeTarget.COMMENT.name());
    }
}
