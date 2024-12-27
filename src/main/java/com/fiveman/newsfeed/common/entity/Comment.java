package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Board board;

    @NotBlank
    @Size(max = 200, message = "내용은 200자 이하이어야 합니다")
    @Column(name = "content",nullable=false)
    private String content;

    @Column(name= "like_count")
    private Integer likeCount = 0;

    public Comment(User user,String content){
        this.user = user;
        this.content = content;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void setBoardId(Board boardId){
        this.board = boardId;
    }

    //TODO 덧글 좋아요 개수 카운트를 업데이트 하기 위해 존재합니다.
    public void updateLikeCount(Integer likeCount){
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
