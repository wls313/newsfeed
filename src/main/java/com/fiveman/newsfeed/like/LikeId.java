package com.fiveman.newsfeed.like;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LikeId {
    private Long targetId;

    private Long parentId;

    private Long userId;

    private String targetType;
}
