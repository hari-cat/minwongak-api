package org.example.aptboardapi.domain.like.service;

import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.domain.like.entity.PostLike;
import org.example.aptboardapi.domain.like.repository.PostLikeRepository;
import org.example.aptboardapi.domain.post.entity.Category;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.post.repository.PostRepository;
import org.example.aptboardapi.domain.user.entity.User;
import org.example.aptboardapi.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostLikeRepository postLikeRepository;

    @InjectMocks
    private PostLikeService postLikeService;

    // 게시물 좋아요
    @Test
    @DisplayName("게시물 좋아요")
    void likePost(){
        Long userId = 1L;
        Long postId = 1L;

        User user = User.create("kiki", "hari", "test1234");
        Post post = Post.create("title", "content", Category.ENVIRONMENT, user);

        given(postLikeRepository.existsByPostIdAndUserId(postId, userId)).willReturn(false);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(postRepository.findByIdAndStatus(postId, Status.ACTIVE)).willReturn(Optional.of(post));

        postLikeService.likePost(userId, postId);

        then(postLikeRepository).should().save(any(PostLike.class));
    }

    // unlikePost
    @Test
    @DisplayName("게시물 좋아요 취소")
    void unlikePost() {
        Long userId = 1L;
        Long postId = 1L;

        User user = User.create("kiki", "hari", "test1234");
        Post post = Post.create("title", "content", Category.ENVIRONMENT, user);
        PostLike postLike = PostLike.create(user, post);

        given(postLikeRepository.findByPostIdAndUserId(postId, userId)).willReturn(Optional.of(postLike));

        postLikeService.unlikePost(userId, postId);

        then(postLikeRepository).should().delete(postLike);
    }

    // Exception
    @Test
    @DisplayName("게시물 좋아요가 존재하지 않을 때 취소 실패")
    void unlikePostNotFound(){
        Long userid = 1L;
        Long postId = 1L;

        given(postLikeRepository.findByPostIdAndUserId(postId, userid)).willReturn(Optional.empty());

        assertThatThrownBy(() -> postLikeService.unlikePost(userid, postId)).isInstanceOf(BusinessException.class).hasMessage(ErrorCode.POST_LIKE_NOT_FOUND.getMessage());

        then(postLikeRepository)
                .should(never())
                .delete(any(PostLike.class));
    }
}