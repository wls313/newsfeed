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

    public Comment(User user,Board board,String content){
        this.user = user;
        this.board = board;
        this.content = content;
    }

    public void updateContent(String content){
        this.content = content;
    }

}
