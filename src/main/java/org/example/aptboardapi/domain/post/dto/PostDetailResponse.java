package org.example.aptboardapi.domain.post.dto;

import org.example.aptboardapi.domain.post.entity.Category;
import org.example.aptboardapi.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostDetailResponse(
        Long id, Category postStatus, String title, String content, String author, LocalDateTime createdAt,
        int readCount, int likeCount, boolean liked
) {
    public static PostDetailResponse from(Post post, int likeCount, boolean liked) {
        return new PostDetailResponse(post.getId(), post.getCategory(), post.getTitle(), post.getContent(), post.getAuthor().getNickname(), post.getCreatedAt(), post.getReadCount(), likeCount, liked);
    }
}
