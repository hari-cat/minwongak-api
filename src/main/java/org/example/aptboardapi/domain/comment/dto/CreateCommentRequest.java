package org.example.aptboardapi.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
        @NotBlank(message = "내용은 필수 입력값입니다.")
        String content,
        Long postId,
        Long parentId
) {
}
