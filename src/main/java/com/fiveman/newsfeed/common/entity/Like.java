package com.fiveman.newsfeed.common.entity;

import com.fiveman.newsfeed.like.LikeId;
import com.fiveman.newsfeed.like.LikeTarget;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Like {

    @EmbeddedId
    private LikeId likeId;

    public Like(Board board, User user) {
        this.likeId = new LikeId(board.getBoardId(), user.getUserId(), LikeTarget.BOARD.name());
    }

    public Like(Comment comment, User user) {
        this.likeId = new LikeId(comment.getCommentId(), user.getUserId(), LikeTarget.COMMENT.name());
    }
}
