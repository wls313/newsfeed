package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*; // Spring Boot 3 이상에서는 jakarta.persistence 사용
import java.time.LocalDateTime;
import com.fiveman.newsfeed.common.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String title;
    private String content;

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
}