package com.fiveman.newsfeed.comment.repository;

import com.fiveman.newsfeed.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
