package org.example.aptboardapi.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.domain.post.dto.CreatePostRequest;
import org.example.aptboardapi.domain.post.dto.PostResponse;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.post.repository.PostRepository;
import org.example.aptboardapi.domain.user.entity.User;
import org.example.aptboardapi.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts(){
        List<Post> posts = postRepository.findAllByStatus(Status.ACTIVE);
        return posts.stream().map(PostResponse::from).toList();
    }

    @Transactional
    public Long createPost(CreatePostRequest request, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND_USER));
        Post post = Post.create(request.title(), request.content(), request.category(), user);

        return postRepository.save(post).getId();
    }
}
