package org.example.aptboardapi.domain.post.dto;

import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.post.entity.Category;

import java.time.LocalDateTime;

public record PostResponse(Category postStatus, String title, String content, String author, LocalDateTime createdAt, int readCount, int likeCount) {
    public static PostResponse from(Post post){
        return new PostResponse(post.getCategory(), post.getTitle(), post.getContent(), post.getAuthor().getNickname(), post.getCreatedAt(), post.getReadCount(), 1);
    }
}
