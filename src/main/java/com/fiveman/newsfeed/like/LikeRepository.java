package com.fiveman.newsfeed.like;

import com.fiveman.newsfeed.common.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
}
