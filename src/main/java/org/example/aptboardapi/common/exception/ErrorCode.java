package org.example.aptboardapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT_VALUE(
            HttpStatus.BAD_REQUEST,
            "C001",
            "잘못된 입력값입니다."
    ),

    METHOD_NOT_ALLOWED(
            HttpStatus.METHOD_NOT_ALLOWED,
            "C002",
            "지원하지 않는 HTTP 메서드입니다."
    ),

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "C003",
            "서버 내부 오류가 발생했습니다."
    ),

    // 게시물
    POST_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "P001",
            "게시물을 찾을 수 없습니다."
    ),

    // 댓글
    COMMENT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "CM001",
            "댓글을 찾을 수 없습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;

}