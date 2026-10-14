package org.example.aptboardapi.domain.post.repository;

import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.domain.post.dto.PostResponse;
import org.example.aptboardapi.domain.post.entity.Category;
import org.example.aptboardapi.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByStatus(Status status);

    @Query("""
            select new org.example.aptboardapi.domain.post.dto.PostResponse(
                p.category, p.title, p.content, p.author.nickname, p.createdAt, p.readCount, 1)
            from Post p
            where p.status = :status
              and (:title is null or p.title like concat('%', :title, '%'))
              and (:content is null or p.content like concat('%', :content, '%'))
              and (:category is null or p.category = :category)
            order by p.createdAt desc
            """)
    List<PostResponse> search(@Param("title") String title, @Param("content") String content, @Param("category") Category category, @Param("status") Status status);

    Optional<Post> findByIdAndStatus(Long id, Status status);

    @Modifying
    @Query("""
    update Post p set p.readCount = p.readCount + 1 where p.id = :id and p.status = :status
""")
    void increaseReadCount(Long id, Status status);
}
