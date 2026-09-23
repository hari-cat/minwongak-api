package org.example.aptboardapi.domain.post.repository;

import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByStatus(Status status);
}
