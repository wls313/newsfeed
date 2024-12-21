package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @JoinColumn(name = "user_id",nullable=false)
    @ManyToOne
    private User user;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

}
