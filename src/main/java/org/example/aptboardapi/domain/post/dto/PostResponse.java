package org.example.aptboardapi.domain.post.dto;

import org.example.aptboardapi.domain.post.entity.PostStatus;

import java.time.LocalDateTime;

public record PostResponse(PostStatus postStatus, String title, String content, String author, LocalDateTime createdAt, int readCount, int likeCount) {
}
