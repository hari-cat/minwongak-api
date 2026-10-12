package org.example.aptboardapi.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aptboardapi.domain.comment.dto.CreateCommentRequest;
import org.example.aptboardapi.domain.comment.service.CommentService;
import org.example.aptboardapi.domain.user.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void createComment(@AuthenticationPrincipal CustomUserDetails principal, @Valid @RequestBody CreateCommentRequest request){
        commentService.createComment(request, principal.getUserId());
    }
}
