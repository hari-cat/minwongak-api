package org.example.aptboardapi.domain.post.repository;

import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByStatus(Status status);
    Optional<Post> findByIdAndStatus(Long id, Status status);

    @Modifying
    @Query("""
    update Post p set p.readCount = p.readCount + 1 where p.id = :id and p.status = :status
""")
    void increaseReadCount(Long id, Status status);
}
