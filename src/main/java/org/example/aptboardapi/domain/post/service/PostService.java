package org.example.aptboardapi.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.domain.post.dto.CreatePostRequest;
import org.example.aptboardapi.domain.post.dto.PostDetailResponse;
import org.example.aptboardapi.domain.post.dto.PostResponse;
import org.example.aptboardapi.domain.post.dto.UpdatePostRequest;
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
    public PostDetailResponse getPost(Long id){
        Post post = postRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(()->new BusinessException(ErrorCode.POST_NOT_FOUND));
        return PostDetailResponse.from(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts(){
        List<Post> posts = postRepository.findAllByStatus(Status.ACTIVE);
        return posts.stream().map(PostResponse::from).toList();
    }

    @Transactional
    public Long createPost(CreatePostRequest request, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND));
        Post post = Post.create(request.title(), request.content(), request.category(), user);

        return postRepository.save(post).getId();
    }

    @Transactional
    public void updatePost(UpdatePostRequest request,Long id, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        if(post.getAuthor() != user){
            throw new BusinessException(ErrorCode.POST_UPDATE_FORBIDDEN);
        }

        post.update(request.title(), request.content(),request.category());
    }

    @Transactional
    public void deletePost(Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        if(post.getAuthor() != user){
            throw new BusinessException(ErrorCode.POST_DELETE_FORBIDDEN);
        }

        post.delete();
    }
}
