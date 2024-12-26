package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*; // Spring Boot 3 이상에서는 jakarta.persistence 사용
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private Integer likeCount = 0;

    @OneToMany(mappedBy = "board",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment){
        commentList.add(comment);
        comment.setBoardId(this);
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
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