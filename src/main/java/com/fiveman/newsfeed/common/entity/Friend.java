package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    private String status;

    // 유저 생성 요청값을 DB에 저장하기 위해 Friend 객체로 만들어주는 정적 팩토리 메서드
    public static Friend of(User fromUser, User toUser) {
        Friend friend = new Friend();
        friend.fromUser = fromUser;
        friend.toUser = toUser;
        return friend;
    }
}
