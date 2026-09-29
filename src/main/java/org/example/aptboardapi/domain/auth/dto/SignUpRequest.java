package org.example.aptboardapi.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        String username,
        @NotBlank(message = "닉네임은 필수 입력값입니다.")
        String nickname,
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        String password
) {
}
