package org.example.aptboardapi.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.aptboardapi.domain.post.entity.Category;

public record CreatePostRequest(@NotBlank(message = "제목은 필수 입력값입니다.") String title, @NotBlank(message = "내용은 필수 입력값입니다.") String content, @NotNull(message = "카테고리는 필수 입력값입니다.") Category category) {
}
