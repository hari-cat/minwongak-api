package org.example.aptboardapi.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.aptboardapi.domain.like.service.PostLikeService;
import org.example.aptboardapi.domain.user.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/{id}")
    public void likePost(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("id") Long postId){
        postLikeService.likePost(customUserDetails.getUserId(), postId);
    }

    @DeleteMapping("/{id}")
    public void unlikePost(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("id") Long postId) {
        postLikeService.unlikePost(customUserDetails.getUserId(), postId);
    }
}
