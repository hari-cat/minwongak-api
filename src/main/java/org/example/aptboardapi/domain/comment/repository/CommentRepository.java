package org.example.aptboardapi.domain.comment.repository;

import org.example.aptboardapi.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
