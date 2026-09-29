package org.example.aptboardapi.domain.like.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.domain.like.entity.PostLike;
import org.example.aptboardapi.domain.like.repository.PostLikeRepository;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.post.repository.PostRepository;
import org.example.aptboardapi.domain.user.entity.User;
import org.example.aptboardapi.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void likePost(Long userId, Long postId) {
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            return;
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findByIdAndStatus(postId, Status.ACTIVE).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        PostLike postLike = PostLike.create(user, post);

        postLikeRepository.save(postLike);
    }

    @Transactional
    public void unlikePost(Long userId, Long postId) {
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId).orElseThrow(() -> new BusinessException(ErrorCode.POST_LIKE_NOT_FOUND));
        postLikeRepository.delete(postLike);
    }
}
