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

    @EmbeddedId
    private FriendId id;

    @ManyToOne
    @MapsId("fromUserId")
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @MapsId("toUserId")
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    private String status;

    public static Friend of(User fromUser, User toUser) {
        Friend friend = new Friend();
        friend.id = new FriendId(fromUser.getUserId(), toUser.getUserId());
        friend.fromUser = fromUser;
        friend.toUser = toUser;
        return friend;
    }
}
