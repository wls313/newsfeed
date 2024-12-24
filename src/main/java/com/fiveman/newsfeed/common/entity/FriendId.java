package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable     // @EmbeddedId 와 매핑됨
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
// Jpa가 복합키를 처리할 수 있도록 명시하는 클래스
public class FriendId implements Serializable {

    private Long fromUserId;
    private Long toUserId;
}
