package org.example.aptboardapi.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.domain.post.dto.CreatePostRequest;
import org.example.aptboardapi.domain.post.dto.PostDetailResponse;
import org.example.aptboardapi.domain.post.dto.PostResponse;
import org.example.aptboardapi.domain.post.dto.UpdatePostRequest;
import org.example.aptboardapi.domain.post.service.PostService;
import org.example.aptboardapi.domain.user.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public PostDetailResponse getPost(@PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails principal) {
        return postService.getPost(principal.getUserId(), id);
    }

    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.getPosts();
    }

    @PostMapping
    public Long createPost(@Valid @RequestBody CreatePostRequest request, @AuthenticationPrincipal CustomUserDetails principal) {
        return postService.createPost(request, principal.getUserId());
    }

    @PatchMapping("/{id}")
    public void updatePost(@PathVariable("id") Long id, @Valid @RequestBody UpdatePostRequest request, @AuthenticationPrincipal CustomUserDetails principal) {
        postService.updatePost(request, id, principal.getUserId());
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails principal) {
        postService.deletePost(id, principal.getUserId());
    }
}
