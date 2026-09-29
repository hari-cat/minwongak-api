package org.example.aptboardapi.domain.post.dto;

import org.example.aptboardapi.domain.post.entity.Category;

public record UpdatePostRequest(String title, String content,
                                Category category) {
}
