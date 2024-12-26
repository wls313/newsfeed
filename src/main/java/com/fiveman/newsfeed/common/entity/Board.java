package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*; // Spring Boot 3 이상에서는 jakarta.persistence 사용
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String title;
    private String content;

    private Integer likeCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
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