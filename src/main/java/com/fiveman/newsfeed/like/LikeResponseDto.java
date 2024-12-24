package com.fiveman.newsfeed.like;

public record LikeResponseDto(Long userId, Long targetId, boolean liked) {
}
