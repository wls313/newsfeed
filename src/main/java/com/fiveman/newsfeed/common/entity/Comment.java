package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long commentId;


    @JoinColumn(name = "user_id",nullable=false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "board_id",nullable=false)
    @ManyToOne
    private Board board;

    @Column(name = "content",nullable=false)
    private String content;

    @Column(name= "like_count")
    private Long likeCount = 0L;

    public Comment(User user,Board board,String content){
        this.user = user;
        this.board = board;
        this.content = content;
    }

    public void updateContent(String content){
        this.content = content;


    }
    //TODO 덧글 좋아요 개수 카운트를 업데이트 하기 위해 존재합니다.
    public void updateLikeCount(Long likeCount){
        this.likeCount = likeCount;
    }

    public void like(User user) {
        if(this.user.getUserId() == user.getUserId()) {
            throw new IllegalArgumentException("본인 글은 좋아요를 누를 수 없습니다.");
        }

        likeCount++;
    }

    public void unlike(User user) {
        likeCount--;
    }
}
