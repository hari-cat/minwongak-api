package org.example.aptboardapi.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.common.response.PageResponse;
import org.example.aptboardapi.domain.post.dto.CreatePostRequest;
import org.example.aptboardapi.domain.post.dto.PostDetailResponse;
import org.example.aptboardapi.domain.post.dto.PostResponse;
import org.example.aptboardapi.domain.post.dto.UpdatePostRequest;
import org.example.aptboardapi.domain.post.entity.Category;
import org.example.aptboardapi.domain.post.service.PostService;
import org.example.aptboardapi.domain.user.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @GetMapping
    public PageResponse<PostResponse> getPosts(@RequestParam(required = false) String title, @RequestParam(required = false) String content, @RequestParam(required = false) Category category, @PageableDefault(size = 2, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, Sort sort) {
        return PageResponse.from(postService.getPosts(title, content, category, pageable));
    }

    @GetMapping("/{id}")
    public PostDetailResponse getPost(@PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails principal) {
        return postService.getPost(principal.getUserId(), id);
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
