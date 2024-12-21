package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "comment")
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


}
